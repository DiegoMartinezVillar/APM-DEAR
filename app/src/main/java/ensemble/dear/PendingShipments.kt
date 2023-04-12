package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class PendingShipments : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_shipments)

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navProfile -> {
                    startActivity(Intent(applicationContext, Profile::class.java))
                }
                R.id.navLogOut -> {
                    startActivity(Intent(applicationContext, ClientLogIn::class.java))
                }
            }
            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

        val buttonScan = findViewById<Button>(R.id.buttonScan)

        val textAddressPackage = findViewById<TextView>(R.id.textTrackingNumber)

        buttonScan.setOnClickListener {
            startActivity(Intent(applicationContext, ARScanner::class.java))
        }

        textAddressPackage.setOnClickListener {
            startActivity(Intent(applicationContext, CourierTrackingDetails::class.java))
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.close()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}