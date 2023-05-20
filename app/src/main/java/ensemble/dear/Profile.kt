package ensemble.dear

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import ensemble.dear.currentTrackings.ClientTrackingDetails
import ensemble.dear.currentTrackings.CurrentTrackings
import ensemble.dear.pendingShipments.PendingShipments


class Profile : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val topAppBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val navigationView = findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.side_menu_name).text = acct.givenName
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.side_menu_email).text = acct.email
            findViewById<TextView>(R.id.text_view_profile_name).text = acct.displayName
            findViewById<TextView>(R.id.text_view_profile_email).text = acct.email

            if (acct.photoUrl != null) {
                Picasso.get().load(acct.photoUrl).into(navigationView.getHeaderView(0).findViewById<ImageView>(R.id.side_menu_profile_pic));
                Picasso.get().load(acct.photoUrl).into(findViewById<ImageView>(R.id.image_view_profile_avatar));
            }
        }

        drawerLayout = findViewById<androidx.drawerlayout.widget.DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        //mark item in drawer as selected

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
                    confirmLogOut()
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

    private fun confirmLogOut() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.confirm_log_out)

        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            gsc.signOut().addOnCompleteListener {
                finish()
                startActivity(Intent(applicationContext, ClientLogIn::class.java))
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }

        builder.show()
    }
}