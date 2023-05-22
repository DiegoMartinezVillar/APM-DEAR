package ensemble.dear.currentTrackings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.Chat
import ensemble.dear.R
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.repository.DeliveryRepository

class ClientTrackingDetailsMap : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_tracking_details_map)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener{
            val intent = Intent(applicationContext, Chat::class.java)
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