package ensemble.dear.currentTrackings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ensemble.dear.R
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ensemble.dear.AddTracking

class CurrentTrackings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_trackings)
        replaceFragment()

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

//        val buttonAddTracking = findViewById<FloatingActionButton>(R.id.buttonAddTracking)
//        buttonAddTracking.setOnClickListener {
//            startActivity(Intent(applicationContext, AddTracking::class.java))
//        }



    }

    private fun replaceFragment() {

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<TrackingsFragment>(R.id.fragment_container_view)
        }
    }
}