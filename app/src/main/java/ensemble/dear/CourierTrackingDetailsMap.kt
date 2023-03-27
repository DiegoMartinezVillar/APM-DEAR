package ensemble.dear

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.addCircle
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import ensemble.dear.place.Place
import ensemble.dear.place.PlaceRenderer
import ensemble.dear.place.PlacesReader
import kotlinx.coroutines.launch

class CourierTrackingDetailsMap : AppCompatActivity() {
    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    private val places: List<Place> by lazy {
        PlacesReader(this).read()
    }

    // The entry point to the Fused Location Provider.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private var lastKnownLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            // From Tiramisu (API 33) onwards, getParcelable() is deprecated and requires a Class argument.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                lastKnownLocation = savedInstanceState.getParcelable(
                    KEY_LOCATION,
                    Location::class.java
                )
                cameraPosition = savedInstanceState.getParcelable(
                    KEY_CAMERA_POSITION,
                    CameraPosition::class.java
                )
            } else { // For older versions, use the deprecated method and suppress the warning.
                @Suppress("DEPRECATION")
                lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
                @Suppress("DEPRECATION")
                cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
            }
        }

        setContentView(R.layout.activity_courier_tracking_details_map)

        // Build map
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                // Get map
                map = mapFragment.awaitMap()

                addClusteredMarkers()

                // Wait for map to finish loading
                map!!.awaitMapLoad()

                // Prompt the user for permission.
                //getLocationPermission()

                // Turn on the My Location layer and the related control on the map.
                //updateLocationUI()

                /*
                Two options for moving the camera:
                    1. Move the camera to the current location of the device.
                    2. Move the camera to match the bounds of all markers.
                Uncomment one of the two options below.
                 */

                // Get the current location of the device and set the position of the map.
                //getDeviceLocation()

                // Ensure all places are visible in the map
                val bounds = LatLngBounds.builder()
                places.forEach { bounds.include(it.latLng) }
                map!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), DEFAULT_PADDING))

            }
        }


        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val confirmButton = findViewById<Button>(R.id.buttonConfirm)
        confirmButton.setOnClickListener{
            val intent = Intent(applicationContext, DeliveryConfirmation::class.java)
            startActivity(intent)
        }

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener{
            val intent = Intent(applicationContext, Chat::class.java)
            startActivity(intent)
        }

        val arButton = findViewById<Button>(R.id.buttonAR)
        arButton.setOnClickListener{
            val intent = Intent(applicationContext, ARDelivery::class.java)
            startActivity(intent)
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener{
            Toast.makeText(applicationContext, "Calling "+ phoneNumberCallText.text, Toast.LENGTH_LONG).show()
        }
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

    /**
     * Adds markers to the map with clustering support.
     */
    private fun addClusteredMarkers() {
        // Create the ClusterManager class and set the custom renderer
        val clusterManager = ClusterManager<Place>(this, map)
        clusterManager.renderer =
            PlaceRenderer(
                this,
                map!!,
                clusterManager
            )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))

        // Add the places to the ClusterManager
        clusterManager.addItems(places)
        clusterManager.cluster()

        // Show polygon
        clusterManager.setOnClusterItemClickListener { item ->
            addCircle(item)
            return@setOnClusterItemClickListener false
        }

        // When the camera starts moving, change the alpha value of the marker to translucent
        map!!.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        map!!.setOnCameraIdleListener {
            // When the camera stops moving, change the alpha value back to opaque
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            // Call clusterManager.onCameraIdle() when the camera stops moving so that re-clustering
            // can be performed when the camera stops moving
            clusterManager.onCameraIdle()
        }
    }

    private var circle: Circle? = null

    /**
     * Adds a [Circle] around the provided [item]
     */
    private fun addCircle(item: Place) {
        circle?.remove()
        circle = map!!.addCircle {
            center(item.latLng)
            radius(1000.0)
            fillColor(ContextCompat.getColor(this@CourierTrackingDetailsMap, R.color.colorPrimaryTranslucent))
            strokeColor(ContextCompat.getColor(this@CourierTrackingDetailsMap, R.color.colorPrimary))
        }
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private fun getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), DEFAULT_ZOOM.toFloat()))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
        updateLocationUI()
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private val TAG = CourierTrackingDetailsMap::class.java.simpleName
        private const val DEFAULT_ZOOM = 14 // Can be any number between 2 and 21, the higher the number the closer the zoom
        private const val DEFAULT_PADDING = 20 // Space in px between the map edge and the cluster items
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1

        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }
}