package ensemble.dear.currentTrackings


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.maps.android.PolyUtil
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import ensemble.dear.BitmapHelper
import ensemble.dear.BuildConfig
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.repository.DeliveryRepository
import ensemble.dear.place.Place
import ensemble.dear.place.PlacesReader
import kotlinx.coroutines.launch
import org.json.JSONObject

class ClientTrackingDetailsMap : AppCompatActivity() {
    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    } // Should be the current package instead of the list of places

    // List of polylines drawn on the map
    private val polylines: MutableList<Polyline> = mutableListOf()

    // The options for the map route polyline
    private val polylineOptions: PolylineOptions by lazy {
        PolylineOptions()
            .color(ContextCompat.getColor(this, R.color.primaryColor))
            .width(5f)
    }

    private val boxIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(applicationContext, R.color.primaryColor)
        BitmapHelper.vectorToBitmap(applicationContext, R.drawable.ic_directions_box_black_24dp, color)
    }

    private val bikeIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(applicationContext, R.color.primaryColor)
        BitmapHelper.vectorToBitmap(applicationContext, R.drawable.ic_directions_bike_black_24dp, color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve camera position from saved instance state.
        if (savedInstanceState != null) {
            // From Tiramisu (API 33) onwards, getParcelable() is deprecated and requires a Class argument.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                cameraPosition = savedInstanceState.getParcelable(
                    KEY_CAMERA_POSITION,
                    CameraPosition::class.java
                )
            } else { // For older versions, use the deprecated method and suppress the warning.
                @Suppress("DEPRECATION")
                cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
            }
        }

        setContentView(R.layout.activity_client_tracking_details_map)


        // Build map
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                // Get map
                map = mapFragment.awaitMap()

                // Set the package and courier markers
                addMarkers()

                // Move the camera to the package destination (this emulates not knowing the courier location yet)
                map!!.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(places[0].latLng, DEFAULT_ZOOM.toFloat())
                )

                // Wait for map to finish loading
                map!!.awaitMapLoad()


                // Turn off the map toolbar and location button
                map!!.uiSettings.isMapToolbarEnabled = false
                map!!.uiSettings.isMyLocationButtonEnabled = false

                // Get the package location and the route to the package destination
                getPackageLocation()

            }
        }

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener{
            /*
            val intent = Intent(applicationContext, Chat::class.java)
            startActivity(intent)
            */
            Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener{
            //Toast.makeText(applicationContext, "Calling "+ phoneNumberCallText.text, Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
        }

        setPageData()
    }

    /**
     * Adds markers to the map with clustering support.
     */
    private fun addMarkers() {
        // Create marker options
        val place = places[0] // this should be the current package destination
        val courier = places[1] // this should be the current courier location

        val packageDestinationMarker =
            map!!.addMarker(MarkerOptions().title(place.address).position(place.latLng).icon(boxIcon))
        val courierMarker =
            map!!.addMarker(MarkerOptions().position(courier.latLng).icon(bikeIcon))

        // When the camera starts moving, change the alpha value of the marker to translucent
        map!!.setOnCameraMoveStartedListener {
            packageDestinationMarker?.alpha = 0.3f
            courierMarker?.alpha = 0.3f
            polylines.forEach{ polyline ->
                polyline.color = ContextCompat.getColor(applicationContext, R.color.primaryColorTranslucent)
            }
        }

        // When the camera stops moving, change the alpha value back to opaque
        map!!.setOnCameraIdleListener {
            packageDestinationMarker?.alpha = 1.0f
            courierMarker?.alpha = 1.0f
            polylines.forEach{ polyline ->
                polyline.color = ContextCompat.getColor(applicationContext, R.color.primaryColor)
            }
        }
    }

    private fun addRoutePolylines() {
        // Set a request for the directions API
        val requestQueue = Volley.newRequestQueue(applicationContext)

        val urlDirections =
            "https://maps.googleapis.com/maps/api/directions/json?" + // places[1] should be the courier's location
                    "origin=${places[1].latLng.latitude},${places[1].latLng.longitude}" +
                    "&destination=${places[0].latLng.latitude},${places[0].latLng.longitude}" + // places[0] should be the client's package destination
                    "&key=${BuildConfig.MAPS_API_KEY}"

        val stringRequest = StringRequest(
            Request.Method.GET,
            urlDirections,
            { response ->
                Log.d("Volley", "Success!")
                // Get the first route from the results
                val route = JSONObject(response).getJSONArray("routes")[0] as JSONObject
                val leg = route.getJSONArray("legs")[0] as JSONObject
                val steps = leg.getJSONArray("steps")

                for (i in 0 until steps.length()) {
                    val step = steps[i] as JSONObject
                    val polyline = step.getJSONObject("polyline")
                    val points = polyline.getString("points")
                    val latLngList = PolyUtil.decode(points)
                    polylineOptions.addAll(latLngList)
                }
                val polyline = map!!.addPolyline(polylineOptions)
                polylines.add(polyline) // Store the polyline so it can be removed later
            },
            { error ->
                Log.e("Volley", "Error: $error")
                Toast.makeText(applicationContext, "Could not get the route", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(stringRequest)

    }

    private fun removePolylines() {
        polylines.forEach { it.remove() }
        if (polylineOptions.points.isNotEmpty()) {
            polylineOptions.points.clear()
        }
    }

    private fun getPackageLocation() {
        addRoutePolylines()
        // Move the camera to fit the route
        val bounds = LatLngBounds.Builder()
        bounds.include(places[0].latLng)
        bounds.include(places[1].latLng)
        map!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), DEFAULT_PADDING))
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
        }
        super.onSaveInstanceState(outState)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = ClientTrackingDetailsMap::class.java.simpleName
        private const val DEFAULT_ZOOM = 14 // Can be any number between 2 and 21, the higher the number the closer the zoom
        private const val DEFAULT_PADDING = 100 // Space in px between the map edge and the cluster items
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_CAMERA_POSITION = "camera_position"
    }

    private fun setPageData() {
        var currentShipmentId: Int? = null

        // connect variables to UI elements
        val packageNumber: TextView = findViewById(R.id.packageNumber)
        val shippingCompany: TextView = findViewById(R.id.shipperCompany)
        val alias: TextView = findViewById(R.id.packageAlias)
        val imgShipperCompany: ImageView = findViewById(R.id.imageView)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentShipmentId = bundle.getInt(TRACKING_ID)
        }

        /* if currentTrackingId is not null, get corresponding tracking data */
        currentShipmentId?.let {
            val currentShipment =
                DeliveryRepository(this@ClientTrackingDetailsMap).deliveriesDAO.getPackageById(it)

            packageNumber.text = "#" + currentShipment?.packageNumber.toString()
            shippingCompany.text = currentShipment?.shipperCompany
            alias.text = currentShipment?.packageAlias

            currentShipment?.shipperCompanyPhoto?.let {
                it1 -> imgShipperCompany.loadUrl(it1)
            }
        }
    }
}