package ensemble.dear.currentTrackings


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.Chat
import ensemble.dear.R

class ClientTrackingDetailsMap : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_tracking_details_map)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener{
            /*
            val intent = Intent(applicationContext, Chat::class.java)
            startActivity(intent)
            */
            Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener{
            //Toast.makeText(applicationContext, "Calling "+ phoneNumberCallText.text, Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
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