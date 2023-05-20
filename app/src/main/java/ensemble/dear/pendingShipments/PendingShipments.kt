package ensemble.dear.pendingShipments

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import ensemble.dear.R
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import ensemble.dear.ARScanner
import ensemble.dear.ClientLogIn
import ensemble.dear.Profile

class PendingShipments : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_shipments)
        replaceFragment()

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

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
                    confirmLogOut()
                }
            }
            menuItem.isChecked = true
            drawerLayout.close()
            true
        }

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

        if (acct != null) {
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.side_menu_name).text = acct.givenName
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.side_menu_email).text = acct.email

            if (acct.photoUrl != null) {
                Picasso.get().load(acct.photoUrl).into(navigationView.getHeaderView(0).findViewById<ImageView>(R.id.side_menu_profile_pic));
            }
        }

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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.close()
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