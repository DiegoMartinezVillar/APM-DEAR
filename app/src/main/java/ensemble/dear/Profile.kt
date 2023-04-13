package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import ensemble.dear.currentTrackings.CurrentTrackings
import ensemble.dear.pendingShipments.PendingShipments

class Profile : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        //mark item in drawer as selected

        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)

        val navCurrentTrackings = navigationView.menu.findItem(R.id.navCurrentTrackings)
        val navProfile = navigationView.menu.findItem(R.id.navProfile)

        navCurrentTrackings.isChecked = false
        navProfile.isChecked = true

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navCurrentTrackings -> {
                    startActivity(Intent(applicationContext, CurrentTrackings::class.java))
                }
                R.id.navPendingShipments -> {
                    startActivity(Intent(applicationContext, PendingShipments::class.java))
                }
                R.id.navLogOut -> {
                    startActivity(Intent(applicationContext, ClientLogIn::class.java))
                }
            }
            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

        val profilePastTracking = findViewById<LinearLayout>(R.id.profilePastTracking)
        profilePastTracking.setOnClickListener {
            startActivity(Intent(applicationContext, ClientTrackingDetails::class.java))
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.close()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}