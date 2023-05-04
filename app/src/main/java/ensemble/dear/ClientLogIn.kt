package ensemble.dear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ensemble.dear.currentTrackings.CurrentTrackings


class ClientLogIn : AppCompatActivity() {

    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_log_in)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToSecondActivity()
        }

        val toolbar =
            findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val buttonClientLogIn = findViewById<Button>(R.id.buttonClientLogIn)
        buttonClientLogIn.setOnClickListener {
            startActivity(Intent(applicationContext, CurrentTrackings::class.java))
        }

        val buttonClientLogInGoogle = findViewById<Button>(R.id.buttonClientLogInGoogle)
        buttonClientLogInGoogle.setOnClickListener {
            startActivityForResult(gsc.signInIntent, 1000)
        }

        val buttonAreYouAuthorizedCourier = findViewById<Button>(R.id.buttonAreYouAuthorizedCourier)
        buttonAreYouAuthorizedCourier.setOnClickListener {
            startActivity(Intent(applicationContext, CourierLogIn::class.java))
        }

        val textViewClickHereToSignUp = findViewById<TextView>(R.id.textViewClickHereToSignUp)
        textViewClickHereToSignUp.setOnClickListener {
            startActivity(Intent(applicationContext, ClientSignUp::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun navigateToSecondActivity() {
        finish()
        startActivity(Intent(applicationContext, CurrentTrackings::class.java))
    }
}