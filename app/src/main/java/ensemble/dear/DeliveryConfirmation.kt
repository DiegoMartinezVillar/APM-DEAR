package ensemble.dear

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import ensemble.dear.drawing.DrawView
import ensemble.dear.pendingShipments.PendingShipments

class DeliveryConfirmation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_confirmation)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val confirmButton = findViewById<Button>(R.id.buttonConfirm)
        confirmButton.setOnClickListener{
            finish()
            val intent = Intent(applicationContext, PendingShipments::class.java)
            startActivity(intent)
        }

        val drawView = findViewById<DrawView>(R.id.drawView)
        drawView.setOnClickListener{
            confirmButton.isEnabled = true
        }

        val clearButton = findViewById<Button>(R.id.buttonClear)
        clearButton.setOnClickListener{
            drawView.clear()
            confirmButton.isEnabled = false
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