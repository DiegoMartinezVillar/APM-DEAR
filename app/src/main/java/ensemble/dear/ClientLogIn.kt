package ensemble.dear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ClientLogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_log_in)

        val courierLogInButton = findViewById<Button>(R.id.buttonAuthorizedCouriers)
        courierLogInButton.setOnClickListener{
            val intent = Intent(this, CourierLogIn::class.java)
            startActivity(intent)
        }

        val googleLogIn = findViewById<Button>(R.id.buttonLogInGoogle)
        googleLogIn.setOnClickListener{
            Toast.makeText(applicationContext, "Opening Google OAuth", Toast.LENGTH_LONG).show()
        }
    }
}