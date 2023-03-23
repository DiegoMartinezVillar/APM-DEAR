package ensemble.dear

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddTracking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tracking)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val buttonAddTracking = findViewById<Button>(R.id.add_button)
        val buttonSearchButton = findViewById<Button>(R.id.search_button)

        buttonAddTracking.setOnClickListener {
            startActivity(Intent(applicationContext, CurrentTrackings::class.java))
        }

        buttonSearchButton.setOnClickListener {
            Toast.makeText(applicationContext,
                android.R.string.search_go, Toast.LENGTH_LONG).show()
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