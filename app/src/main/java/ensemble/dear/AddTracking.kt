package ensemble.dear

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import ensemble.dear.currentTrackings.CurrentTrackings
import ensemble.dear.database.entity.Delivery
import ensemble.dear.database.repository.ClientRepository
import ensemble.dear.database.repository.DeliveryRepository
import ensemble.dear.database.repository.PackageRepository

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

class AddTracking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tracking)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val buttonAddTracking = findViewById<Button>(R.id.add_button)
        val buttonSearchButton = findViewById<Button>(R.id.search_button)
        val inputTrackingNumber = findViewById<TextView>(R.id.tracking_code)
        val inputAdditionalInstructions = findViewById<EditText>(R.id.additionalInstructionsText)
        val inputAlias = findViewById<EditText>(R.id.alias_text)

        buttonAddTracking.setOnClickListener {

            val searchTrackingNumberText = inputTrackingNumber.text.toString()
            if(searchTrackingNumberText != "" && inputAlias.text.toString() != "" ) {

                val trackingNumber = searchTrackingNumberText.toInt()
                val packageFound = PackageRepository(this@AddTracking).getPackageByNumber(trackingNumber)

                if(packageFound != null) {

                    val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

                    if(acct != null){
                        val idOfLoggedUser = ClientRepository(this@AddTracking)
                            .getClientByEmail(acct.email.toString())

                        val delivery = Delivery(0, packageFound.packageNumber,
                            inputAdditionalInstructions.text.toString(),
                            inputAlias.text.toString(), "", idOfLoggedUser)

                        DeliveryRepository(this@AddTracking).insert(delivery)

                        startActivity(Intent(applicationContext, CurrentTrackings::class.java))
                    }
                }
            } else {
                Toast.makeText(applicationContext,
                    "tracking number and ALIAS cannot be empty", Toast.LENGTH_LONG).show()
            }
        }

        buttonSearchButton.setOnClickListener {

            val searchTrackingNumberText = inputTrackingNumber.text.toString()
            if(searchTrackingNumberText != ""){
                val trackingNumber = searchTrackingNumberText.toInt()

                val packageFound = PackageRepository(this@AddTracking).packageDAO.getPackageByNumber(trackingNumber)

                if(packageFound != null) {
                    buttonAddTracking.isEnabled = true

                    Toast.makeText(applicationContext,
                        "tracking found!", Toast.LENGTH_LONG).show()
                    hideKeyboard()

                } else {
                    buttonAddTracking.isEnabled = false

                    Toast.makeText(applicationContext,
                        "tracking not found :(", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext,
                    "tracking number cannot be empty", Toast.LENGTH_LONG).show()
            }
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