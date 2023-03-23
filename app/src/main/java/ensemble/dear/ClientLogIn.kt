package ensemble.dear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ClientLogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_log_in)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val buttonClientLogIn = findViewById<Button>(R.id.buttonClientLogIn)
        buttonClientLogIn.setOnClickListener{
            startActivity(Intent(applicationContext, CurrentTrackings::class.java))
        }

        val buttonClientLogInGoogle = findViewById<Button>(R.id.buttonClientLogInGoogle)
        buttonClientLogInGoogle.setOnClickListener{
            Toast.makeText(applicationContext, "Opening Google OAuth", Toast.LENGTH_LONG).show()
        }

        val buttonAreYouAuthorizedCourier = findViewById<Button>(R.id.buttonAreYouAuthorizedCourier)
        buttonAreYouAuthorizedCourier.setOnClickListener{
            startActivity(Intent(applicationContext, CourierLogIn::class.java))
        }

        val textViewClickHereToSignUp = findViewById<TextView>(R.id.textViewClickHereToSignUp)
        textViewClickHereToSignUp.setOnClickListener{
            startActivity(Intent(applicationContext, ClientSignUp::class.java))
        }
    }
}