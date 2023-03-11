package ensemble.dear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class CourierLogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_courier_log_in)

        val buttonCourierLogIn = findViewById<Button>(R.id.buttonCourierLogIn)
        buttonCourierLogIn.setOnClickListener{
            Toast.makeText(applicationContext, "Pending shipments", Toast.LENGTH_LONG).show()
        }
    }
}