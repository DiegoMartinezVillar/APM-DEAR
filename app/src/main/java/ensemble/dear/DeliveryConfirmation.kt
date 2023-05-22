package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ensemble.dear.currentTrackings.TRACKING_ID
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.repository.DeliveryRepository
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.drawing.DrawView
import ensemble.dear.pendingShipments.PendingShipments

class DeliveryConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_confirmation)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val drawView = findViewById<DrawView>(R.id.drawView)

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener{
            drawView.clear()
        }

        val confirmButton = findViewById<Button>(R.id.buttonConfirm)
        confirmButton.setOnClickListener{
            val intent = Intent(applicationContext, PendingShipments::class.java)
            startActivity(intent)
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
        val imgShipperCompany: ImageView = findViewById(R.id.imageView)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentShipmentId = bundle.getInt(TRACKING_ID)
        }

        /* if currentTrackingId is not null, get corresponding tracking data */
        currentShipmentId?.let {
            val currentShipment = PackageRepository(this@DeliveryConfirmation).packageDAO.getPackageByNumber(it)

            packageNumber.text = "#" + currentShipment?.packageNumber.toString()
            shippingCompany.text = currentShipment?.shipperCompany

            currentShipment?.shipperCompanyPhoto?.let {
                    it1 -> imgShipperCompany.loadUrl(it1)
            }
        }
    }
}