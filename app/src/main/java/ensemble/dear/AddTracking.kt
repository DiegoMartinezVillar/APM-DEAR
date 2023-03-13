package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AddTracking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tracking)

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
}