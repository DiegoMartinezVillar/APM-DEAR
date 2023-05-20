package ensemble.dear

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import ensemble.dear.currentTrackings.CurrentTrackings
import ensemble.dear.database.AppDatabase
import ensemble.dear.database.repository.AuthorizedCourierRepository
import ensemble.dear.pendingShipments.PendingShipments


class ClientLogIn : AppCompatActivity() {

    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var attemptType: String

    private fun courierEmailIsAuthorized(email: String): Boolean {
        val authorizedCouriers = AuthorizedCourierRepository(this).getAllAuthorizedCouriers()
        for (courier in authorizedCouriers) {
            if (courier.email == email) {
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_log_in)
        AppDatabase.getInstance(this)

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
            //startActivity(Intent(applicationContext, CurrentTrackings::class.java))
            Toast.makeText(applicationContext, "Non-priority feature.", Toast.LENGTH_SHORT).show()
        }

        val buttonClientLogInGoogle = findViewById<Button>(R.id.buttonClientLogInGoogle)
        buttonClientLogInGoogle.setOnClickListener {
            resultLauncher.launch(gsc.signInIntent)
            attemptType = "client"
        }

        val buttonAreYouAuthorizedCourier = findViewById<Button>(R.id.buttonAreYouAuthorizedCourier)
        buttonAreYouAuthorizedCourier.setOnClickListener {
            resultLauncher.launch(gsc.signInIntent)
            attemptType = "courier"
        }

        val textViewClickHereToSignUp = findViewById<TextView>(R.id.textViewClickHereToSignUp)
        textViewClickHereToSignUp.setOnClickListener {
            //startActivity(Intent(applicationContext, ClientSignUp::class.java))
            Toast.makeText(applicationContext, "Non-priority feature", Toast.LENGTH_SHORT).show()
        }
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    task.getResult(ApiException::class.java)
                    val email = GoogleSignIn.getLastSignedInAccount(this)?.email.toString()
                    if ((attemptType == "courier") and (!courierEmailIsAuthorized(email))) {
                        Toast.makeText(
                            applicationContext,
                            "Email not recognized as courier",
                            Toast.LENGTH_SHORT
                        ).show()
                        gsc.signOut()
                    } else if ((attemptType == "client") and (courierEmailIsAuthorized(email))) {
                        Toast.makeText(
                            applicationContext,
                            "Email only valid for courier login",
                            Toast.LENGTH_SHORT
                        ).show()
                        gsc.signOut()
                    } else {
                        navigateToSecondActivity()
                    }
                } catch (e: ApiException) {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    private fun navigateToSecondActivity() {
        finish()
        if (courierEmailIsAuthorized(GoogleSignIn.getLastSignedInAccount(this)?.email.toString())) {
            startActivity(Intent(applicationContext, PendingShipments::class.java))
        } else {
            startActivity(Intent(applicationContext, CurrentTrackings::class.java))
        }
    }

    override fun onBackPressed() {
        Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.also { startActivity(it) }
    }
}