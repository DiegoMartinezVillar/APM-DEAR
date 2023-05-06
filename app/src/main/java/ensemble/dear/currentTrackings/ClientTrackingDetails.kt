package ensemble.dear.currentTrackings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.Chat
import ensemble.dear.R

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
            startActivity(intent)
        }

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener {
            val intent = Intent(applicationContext, Chat::class.java)
            startActivity(intent)
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Calling " + phoneNumberCallText.text,
                Toast.LENGTH_LONG
            ).show()
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


        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentTrackingId = bundle.getInt(TRACKING_ID)
        }

        /* if currentTrackingId is not null, get corresponding tracking data */
        currentTrackingId?.let {
            val currentTracking = trackingDetailViewModel.getTrackingForId(it)
            packageContent.text = currentTracking?.packageContent

            address.text = currentTracking?.address
            packageNumber.text = "#" + currentTracking?.packageNumber.toString()
            shippingCompany.text = currentTracking?.packageContent//currentTracking?.shipperCompany
            arrivalDate.text = currentTracking?.arrivalDate.toString()

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