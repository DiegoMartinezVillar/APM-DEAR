package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class PendingShipments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_shipments)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val buttonScan = findViewById<Button>(R.id.buttonScan)

        val textAddressPackage = findViewById<TextView>(R.id.textTrackingNumber)

        buttonScan.setOnClickListener {
            startActivity(Intent(applicationContext, ARScanner::class.java))
        }

        textAddressPackage.setOnClickListener {
            startActivity(Intent(applicationContext, CourierTrackingDetails::class.java))
        }
    }
}