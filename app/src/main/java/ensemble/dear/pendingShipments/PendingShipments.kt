package ensemble.dear.pendingShipments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import ensemble.dear.R
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ensemble.dear.ARScanner

class PendingShipments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_shipments_test)
        replaceFragment()

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val buttonScan = findViewById<Button>(R.id.buttonScan)

        buttonScan.setOnClickListener {
            startActivity(Intent(applicationContext, ARScanner::class.java))
        }

    }

    private fun replaceFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ShipmentsFragment>(R.id.fragment_container_view)
        }
    }
}