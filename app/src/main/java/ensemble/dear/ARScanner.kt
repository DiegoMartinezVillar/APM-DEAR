package ensemble.dear

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ARScanner : AppCompatActivity() {
    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arscanner)

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        val imageCaptureButton = findViewById<FloatingActionButton>(R.id.buttonImageCapture)
        imageCaptureButton.setOnClickListener {
            takePhoto()
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        val viewFinder = findViewById<androidx.camera.view.PreviewView>(R.id.viewFinder)
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Set up the preview use case to display camera preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewFinder.surfaceProvider)
                }

            // Set up the capture use case to allow users to take photos
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                @ExperimentalGetImage override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    super.onCaptureSuccess(imageProxy)
                    val mediaImage = imageProxy.image
                    if (mediaImage != null) {
                        // get the scale factor of the imageProxy
                        Log.e(TAG, imageProxy.height.toString() + "," + imageProxy.width.toString())
                        val image = InputImage.fromMediaImage(mediaImage,
                                                            imageProxy.imageInfo.rotationDegrees)
                        processPicture(image)
                        // Close ImageProxy after processing the QR codes
                        imageProxy.close()
                    }
                }
            }
        )
    }

    private fun processPicture(image: InputImage) {
        // Use QR code scanner
        val scanner_options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_QR_CODE)
            .build()
        // Get the scanner
        val scanner = BarcodeScanning.getClient()

        val result = scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isEmpty()) {
                    Toast.makeText(this, "No QR code found", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }
                // Task completed successfully
                for (barcode in barcodes) {
                    //val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    if (corners != null) {
                        // Get the offset to display the popup
                        var offsetX = corners[0].x
                        var offsetY = (corners[0].y + corners[2].y) / 2

                        val rawValue = barcode.rawValue

                        // See API reference for complete list of supported types
                        when (barcode.valueType) {
                            Barcode.TYPE_TEXT -> {
                                // Inflate view and set the QR content
                                val view = LayoutInflater.from(this)
                                    .inflate(R.layout.qr_popup_window, null)
                                view.findViewById<TextView>(R.id.text_view_text).text = rawValue

                                // Show popup window
                                val popupWindow = PopupWindow(
                                    view,
                                    WindowManager.LayoutParams.WRAP_CONTENT,
                                    WindowManager.LayoutParams.WRAP_CONTENT,
                                    true
                                )
                                // Get image size
                                val imageWidth = image.width
                                val imageHeight = image.height
                                //Log.e(TAG, imageWidth.toString() + "," + imageHeight.toString())

                                // get preview view size
                                val previewView = findViewById<androidx.camera.view.PreviewView>(R.id.viewFinder)
                                val previewViewWidth = previewView.width
                                val previewViewHeight = previewView.height
                                //Log.e(TAG, previewViewWidth.toString() + "," + previewViewHeight.toString())

                                // Rescale the coordinates of the QR code
                                val scaleFactorX = imageWidth.toFloat() / previewViewWidth.toFloat()
                                val scaleFactorY = imageHeight.toFloat() / previewViewHeight.toFloat()
                                //Log.e(TAG, "Prior center coordinates: $offsetX, $offsetY")
                                offsetX = (offsetX / scaleFactorX).toInt()
                                offsetY = (offsetY / scaleFactorY).toInt()
                                //Log.e(TAG, "Center coordinates: $offsetX, $offsetY")

                                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, offsetX, offsetY)
                                // do custom actions after outside touch
                                popupWindow.setOnDismissListener {
                                    // do something
                                }
                            }
                            else -> {
                                Toast.makeText(
                                    this,
                                    "QR code type not supported",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }.addOnFailureListener {
                // Task failed with an exception
                Toast.makeText(this, "Barcode scanning failed", Toast.LENGTH_LONG).show()
                Log.e(TAG, "Barcode scanning failed: ${it.message}", it)
            }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (allPermissionsGranted()) {
                    startCamera()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
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

    companion object {
        private val TAG = ARScanner::class.java.simpleName
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                android.Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}