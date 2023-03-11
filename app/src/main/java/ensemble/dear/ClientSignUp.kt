package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClientSignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_sign_up)

        val buttonClientSignUp = findViewById<Button>(R.id.buttonClientSignUp)
        buttonClientSignUp.setOnClickListener{
            startActivity(Intent(applicationContext, CurrentTrackings::class.java))
        }
    }
}