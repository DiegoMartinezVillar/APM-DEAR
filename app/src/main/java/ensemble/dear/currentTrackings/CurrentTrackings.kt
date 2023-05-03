package ensemble.dear.currentTrackings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import ensemble.dear.R
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.AddTracking
import ensemble.dear.ClientLogIn
import ensemble.dear.Profile

class CurrentTrackings : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_trackings)
        replaceFragment()

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

        val buttonAddTracking = findViewById<FloatingActionButton>(R.id.buttonAddTracking)
        buttonAddTracking.setOnClickListener {
            /*val db = DatabaseConnection(
                LoginInfo("marialgarcia", "myNewPassword"),
                "deAR",
                "deAR")

            val collection = db.collectionOf<Tracking_test>("trackings")

            // Find all trackings
            collection.find().forEach {
                println(it.packageNumber)
                Log.i("package number", it.packageNumber.toString())
            }


*/
            startActivity(Intent(applicationContext, AddTracking::class.java))
        }
    }

    private fun replaceFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<TrackingsFragment>(R.id.fragment_container_view)
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