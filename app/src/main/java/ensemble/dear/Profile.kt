package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profilePastTracking = findViewById<LinearLayout>(R.id.profilePastTracking)
        profilePastTracking.setOnClickListener{
            startActivity(Intent(applicationContext, ClientTrackingDetails::class.java))
        }
    }
}