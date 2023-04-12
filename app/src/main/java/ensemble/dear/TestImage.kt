package ensemble.dear

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast

class TestImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_image)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val imageURI = getImageURI()
        if (imageURI != null) {
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageURI(imageURI)
        }

    }

    private fun getImageURI(): Uri? {
        var imageUri: Uri? = null
        // From Tiramisu (API 33) onwards, getParcelable() is deprecated and requires a Class argument.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            imageUri = intent.getParcelableExtra("imageUri", Uri::class.java)
        } else { // For older versions, use the deprecated method and suppress the warning.
            @Suppress("DEPRECATION")
            imageUri = intent.getParcelableExtra("imageUri")
        }
        return imageUri
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(applicationContext, "Back button pressed", Toast.LENGTH_LONG).show()
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}