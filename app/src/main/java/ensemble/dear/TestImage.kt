package ensemble.dear

import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TestImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_image)

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
}