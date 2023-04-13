package ensemble.dear

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.ImageView

class ARDeliveryImageCheck : AppCompatActivity() {
    lateinit var imageUriDeletion : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ardelivery_image_check)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        val imageURI = getImageURI()
        if (imageURI != null) {
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageURI(imageURI)
            imageUriDeletion = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                    imageURI.lastPathSegment)
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

    private fun deleteImage() {
        // Delete the image from the MediaStore.
        contentResolver.delete(imageUriDeletion, null, null)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                deleteImage()
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        deleteImage()
        onBackPressedDispatcher.onBackPressed()
    }
}