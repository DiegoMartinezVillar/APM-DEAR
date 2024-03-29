package ensemble.dear.currentTrackings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import ensemble.dear.AddTracking
import ensemble.dear.ClientLogIn
import ensemble.dear.profile.ClientProfile
import ensemble.dear.R


class CurrentTrackings : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        setContentView(R.layout.activity_current_trackings)
        replaceFragment()

        val topAppBar =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        val navigationView =
            findViewById<com.google.android.material.navigation.NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navProfile -> {

                    startActivity(Intent(applicationContext, ClientProfile::class.java))
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

        val buttonAddTracking = findViewById<FloatingActionButton>(R.id.buttonAddTracking)
        buttonAddTracking.setOnClickListener {
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
        }
        else {
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }.also { startActivity(it) }
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