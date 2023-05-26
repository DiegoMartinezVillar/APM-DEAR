package ensemble.dear

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ensemble.dear.currentTrackings.TRACKING_ID
import ensemble.dear.currentTrackings.adapter.loadUrl
import ensemble.dear.database.repository.PackageRepository
import ensemble.dear.drawing.DrawView
import ensemble.dear.pendingShipments.PendingShipments
import java.io.ByteArrayOutputStream


class DeliveryConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_confirmation)

        setPageData()

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val confirmButton = findViewById<Button>(R.id.buttonConfirm)
        val drawView = findViewById<DrawView>(R.id.drawView)

        drawView.setOnClickListener{
            confirmButton.isEnabled = true
        }

        confirmButton.setOnClickListener{
            saveSignature(drawView)
            finish()
            val intent = Intent(applicationContext, PendingShipments::class.java)
            startActivity(intent)
        }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener{
            drawView.clear()
            confirmButton.isEnabled = false
        }

    }

    fun saveSignature(drawView: DrawView,) {

        val bitmap = drawView.getBitmapFromView()

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()

        val packageNumber: TextView = findViewById(R.id.packageNumber)
        val packageNumberInt = packageNumber.text.toString().substring(1).toInt()

        PackageRepository(this@DeliveryConfirmation).updateSignature(byteArray, packageNumberInt)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}