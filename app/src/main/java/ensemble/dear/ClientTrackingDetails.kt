package ensemble.dear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.currentTrackings.TRACKING_ID

class ClientTrackingDetails : AppCompatActivity() {

    private val trackingDetailViewModel by viewModels<TrackingDetailViewModel> {
        TrackingDetailViewModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_tracking_details)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val locateButton = findViewById<Button>(R.id.buttonLocate)
        locateButton.setOnClickListener{
            val intent = Intent(applicationContext, ClientTrackingDetailsMap::class.java)
            startActivity(intent)
        }

        val chatButton = findViewById<FloatingActionButton>(R.id.buttonChat)
        chatButton.setOnClickListener{
            val intent = Intent(applicationContext, Chat::class.java)
            startActivity(intent)
        }

        val phoneNumberCallText = findViewById<TextView>(R.id.textPhoneNumberCall)
        phoneNumberCallText.setOnClickListener{
            Toast.makeText(applicationContext, "Calling "+ phoneNumberCallText.text, Toast.LENGTH_LONG).show()
        }

        setPageData()
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

    private fun setPageData() {
        var currentTrackingId: Int? = null

        /* Connect variables to UI elements. */
        val packageContent: TextView = findViewById(R.id.textView3)
//        val flowerImage: ImageView = findViewById(R.id.flower_detail_image)
//        val flowerDescription: TextView = findViewById(R.id.flower_detail_description)
//        val removeFlowerButton: Button = findViewById(R.id.remove_button)

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentTrackingId = bundle.getInt(TRACKING_ID)
        }

        /* If currentFlowerId is not null, get corresponding flower and set name, image and
        description */
        currentTrackingId?.let {
            val currentTracking = trackingDetailViewModel.getTrackingForId(it)
            packageContent.text = currentTracking?.packageContent


//            if (currentFlower?.image == null) {
//                flowerImage.setImageResource(R.drawable.rose)
//            } else {
//                flowerImage.setImageResource(currentFlower.image)
//            }
//            flowerDescription.text = currentFlower?.description
//
//            removeFlowerButton.setOnClickListener {
//                if (currentFlower != null) {
//                    flowerDetailViewModel.removeFlower(currentFlower)
//                }
//                finish()
//            }
        }
    }
}