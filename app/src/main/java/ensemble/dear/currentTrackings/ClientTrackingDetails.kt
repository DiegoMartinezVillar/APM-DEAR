package ensemble.dear.currentTrackings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.DELIVERED_STATE
import ensemble.dear.database.IN_DELIVERY_STATE
import ensemble.dear.database.ON_THE_WAY_STATE
import ensemble.dear.database.PRE_ADMISSION_STATE

class ClientTrackingDetails : AppCompatActivity() {

    private val trackingDetailViewModel by viewModels<TrackingDetailViewModel> {
        TrackingDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_tracking_details)

        val toolbar =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val locateButton = findViewById<Button>(R.id.buttonLocate)
        locateButton.setOnClickListener {
            val intent = Intent(applicationContext, ClientTrackingDetailsMap::class.java)
            val packageNumber: TextView = findViewById(R.id.detailPackageNumber)
            val packNumber = packageNumber.text.toString().substring(1).toInt()
            intent.putExtra(TRACKING_ID, packNumber)
            startActivity(intent)
        }

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener {
            /*
            val intent = Intent(applicationContext, Chat::class.java)
            startActivity(intent)
            */
            Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener {
            //Toast.makeText(applicationContext, "Calling " + phoneNumberCallText.text, Toast.LENGTH_LONG).show()
            //Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+1 (415)-553-0123", null)))
        }

        setPageData()
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

    private fun setPageData() {
        var currentTrackingId: Int? = null

        /* Connect variables to UI elements. */
        val packageContent: TextView = findViewById(R.id.textView3)

        val preadmissionState: ImageView = findViewById(R.id.preadmissionCheck)
        val inDeliveryState: ImageView = findViewById(R.id.inDeliveryCheck)
        val onTheWayState: ImageView = findViewById(R.id.onTheWayCheck)
        val deliveredState: ImageView = findViewById(R.id.deliveredCheck)

        val address: TextView = findViewById(R.id.addressText)
        val packageNumber: TextView = findViewById(R.id.detailPackageNumber)
        val shippingCompany: TextView = findViewById(R.id.shippingCompany)
        val arrivalDate: TextView = findViewById(R.id.arrivalTime)
        val imgShipperCompany: ImageView = findViewById(R.id.imageView)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentTrackingId = bundle.getInt(TRACKING_ID)
        }

        /* if currentTrackingId is not null, get corresponding tracking data */
        currentTrackingId?.let {
            val currentTracking = trackingDetailViewModel.getTrackingForId(it)

            packageContent.text = currentTracking?.packageAlias
            address.text = currentTracking?.address.toString()
            packageNumber.text = "#" + currentTracking?.packageNumber.toString()
            shippingCompany.text = currentTracking?.shipperCompany
            arrivalDate.text = currentTracking?.arrivalDate?.dayOfMonth.toString() + " " +
                    currentTracking?.arrivalDate?.month?.name?.lowercase() + " " +
                    currentTracking?.arrivalDate?.year.toString()

            currentTracking?.shipperCompanyPhoto?.let {
                it1 -> imgShipperCompany.loadUrl(it1)
            }

            when (currentTracking?.state) {
                PRE_ADMISSION_STATE -> {
                    preadmissionState.setImageResource(android.R.drawable.checkbox_on_background)
                    onTheWayState.setImageResource(android.R.drawable.checkbox_off_background)
                    inDeliveryState.setImageResource(android.R.drawable.checkbox_off_background)
                    deliveredState.setImageResource(android.R.drawable.checkbox_off_background)
                }
                ON_THE_WAY_STATE -> {
                    preadmissionState.setImageResource(android.R.drawable.checkbox_on_background)
                    onTheWayState.setImageResource(android.R.drawable.checkbox_on_background)
                    inDeliveryState.setImageResource(android.R.drawable.checkbox_off_background)
                    deliveredState.setImageResource(android.R.drawable.checkbox_off_background)
                }
                IN_DELIVERY_STATE -> {
                    preadmissionState.setImageResource(android.R.drawable.checkbox_on_background)
                    onTheWayState.setImageResource(android.R.drawable.checkbox_on_background)
                    inDeliveryState.setImageResource(android.R.drawable.checkbox_on_background)
                    deliveredState.setImageResource(android.R.drawable.checkbox_off_background)
                }
                DELIVERED_STATE -> {
                    preadmissionState.setImageResource(android.R.drawable.checkbox_on_background)
                    onTheWayState.setImageResource(android.R.drawable.checkbox_on_background)
                    inDeliveryState.setImageResource(android.R.drawable.checkbox_on_background)
                    deliveredState.setImageResource(android.R.drawable.checkbox_on_background)
                }
            }
        }
    }
}