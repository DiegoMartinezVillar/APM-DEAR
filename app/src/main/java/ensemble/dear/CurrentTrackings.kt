package ensemble.dear

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CurrentTrackings : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
        android.R.string.ok, Toast.LENGTH_LONG).show()
    }

    private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.cancel, Toast.LENGTH_LONG).show()
    }

    fun confirmDeletionAlert(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Deletion")
        builder.setMessage("Are you sure you want to delete this tracking?")
        builder.setPositiveButton("Delete", DialogInterface.OnClickListener(function = positiveButtonClick))
        builder.setNegativeButton(android.R.string.cancel, negativeButtonClick )
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_trackings)

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
        val buttonDeleteTracking = findViewById<Button>(R.id.buttonDeleteTracking)

        val textTrackingNumber = findViewById<TextView>(R.id.textTrackingNumber)

        buttonAddTracking.setOnClickListener {
            startActivity(Intent(applicationContext, AddTracking::class.java))
        }

        buttonDeleteTracking.setOnClickListener {
            confirmDeletionAlert()
        }

        textTrackingNumber.setOnClickListener {
            startActivity(Intent(applicationContext, ClientTrackingDetails::class.java))
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