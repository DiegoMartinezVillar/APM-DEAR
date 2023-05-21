package ensemble.dear.pendingShipments

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
import ensemble.dear.DeliveryConfirmation
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.repository.DeliveryRepository
import ensemble.dear.database.repository.PackageRepository

class CourierTrackingDetails : AppCompatActivity() {

    private val shipmentDetailViewModel by viewModels<ShipmentsDetailViewModel> {
        ShipmentsDetailViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier_tracking_details)

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

        val liveRouteButton = findViewById<Button>(R.id.buttonLiveRoute)
        liveRouteButton.setOnClickListener{
            val intent = Intent(applicationContext, CourierTrackingDetailsMap::class.java)
            startActivity(intent)
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener{
            Toast.makeText(applicationContext, "Calling "+ phoneNumberCallText.text, Toast.LENGTH_LONG).show()
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
        var currentShipmentId: Int? = null

        // connect variables to UI elements
        val packageContent: TextView = findViewById(R.id.textView3)
        val address: TextView = findViewById(R.id.addressText)
        val packageNumber: TextView = findViewById(R.id.deliveryNumber)
        val shippingCompany: TextView = findViewById(R.id.shipperCompany)
        val imgShipperCompany: ImageView = findViewById(R.id.imageView)

        val additionalInstructions: TextView = findViewById(R.id.additionalInstructions)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentShipmentId = bundle.getInt(SHIPMENT_ID)
        }

        /* if currentTrackingId is not null, get corresponding tracking data */
        currentShipmentId?.let {
            val currentShipment = shipmentDetailViewModel.getShipmentForId(it)
            //packageContent.text = currentShipment?.shipperCompany

            address.text = currentShipment?.address
            packageNumber.text = "#" + currentShipment?.packageNumber.toString()
            shippingCompany.text = currentShipment?.shipperCompany


            val packageFound = DeliveryRepository(this@CourierTrackingDetails)
                .deliveriesDAO.getPackageById(it)

            if(packageFound != null){
                additionalInstructions.text = packageFound?.additionalInstructions
            } else {
                additionalInstructions.text = ""
            }

            currentShipment?.shipperCompanyPhoto?.let {
                    it1 -> imgShipperCompany.loadUrl(it1)
            }

            //arrivalDate.text = currentShipment?.arrivalDate?.dayOfMonth.toString() + " " +
            //        currentShipment?.arrivalDate?.month?.name?.lowercase() + " " + currentShipment?.arrivalDate?.year.toString()

        }
    }
}