package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DeliveryConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_confirmation)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener{
            Toast.makeText(applicationContext, "Content cleared!", Toast.LENGTH_LONG).show()
        }

        val confirmButton = findViewById<Button>(R.id.buttonConfirm)
        confirmButton.setOnClickListener{
            Toast.makeText(applicationContext, "Delivery confirmed!", Toast.LENGTH_LONG).show()
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