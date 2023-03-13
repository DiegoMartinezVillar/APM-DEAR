package ensemble.dear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ARScanner : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arscanner)

        val cameraButton = findViewById<FloatingActionButton>(R.id.cameraButton)

        cameraButton.setOnClickListener {
            Toast.makeText(applicationContext, "open camera", Toast.LENGTH_LONG).show()
        }
    }

}