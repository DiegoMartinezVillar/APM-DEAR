package ensemble.dear

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentTrackings : AppCompatActivity() {

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
        android.R.string.ok, Toast.LENGTH_LONG).show()
    }

    private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(applicationContext,
            android.R.string.cancel, Toast.LENGTH_LONG).show()
    }

    fun confirmDeletionAlert(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Deletion")
        builder.setMessage("Are you sure you want to delete this tracking?")
        builder.setPositiveButton("Delete", DialogInterface.OnClickListener(function = positiveButtonClick))
        builder.setNegativeButton(android.R.string.cancel, negativeButtonClick )
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_trackings)

        val buttonAddTracking = findViewById<FloatingActionButton>(R.id.buttonAddTracking)
        val buttonDeleteTracking = findViewById<Button>(R.id.buttonDeleteTracking)

        val textTrackingNumber = findViewById<TextView>(R.id.textTrackingNumber)

        buttonAddTracking.setOnClickListener {
            startActivity(Intent(applicationContext, AddTracking::class.java))
        }

        buttonDeleteTracking.setOnClickListener {
            confirmDeletionAlert()
        }

        textTrackingNumber.setOnClickListener {
            startActivity(Intent(applicationContext, ClientTrackingDetails::class.java))
        }

    }



}