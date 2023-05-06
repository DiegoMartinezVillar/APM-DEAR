package ensemble.dear

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ensemble.dear.currentTrackings.CurrentTrackings
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

        buttonAddTracking.setOnClickListener {

            startActivity(Intent(applicationContext, CurrentTrackings::class.java))
        }

        buttonSearchButton.setOnClickListener {

            val searchTrackingNumberText = inputTrackingNumber.text.toString()
            if(searchTrackingNumberText != ""){
                val trackingNumber = searchTrackingNumberText.toInt()

                val packageFound = PackageRepository(this@AddTracking)
                    .packageDAO.getPackageByNumber(trackingNumber)

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