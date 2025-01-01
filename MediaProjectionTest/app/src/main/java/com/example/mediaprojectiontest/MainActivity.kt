package com.example.mediaprojectiontest

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    private lateinit var screenCaptureLauncher: ActivityResultLauncher<Intent>
    private lateinit var mediaProjectionManager: MediaProjectionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
                )
        }

        screenCaptureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                // If permission granted, start the service with result data
                val intent = Intent(applicationContext, ScreenCaptureService::class.java).apply {
                    putExtra("RESULT_CODE", result.resultCode)
                    putExtra("DATA", result.data)
                    action = ScreenCaptureService.Actions.START.toString()  // Start action for service
                }
                startService(intent) // Start the ScreenCaptureService to handle the projection
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }


        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.generatedimage)
//
//        // Step 2: Recognize text using ML Kit
//        recognizeTextFromBitmap(bitmap)

        setContent {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    val projectionIntent = mediaProjectionManager.createScreenCaptureIntent()
                    screenCaptureLauncher.launch(projectionIntent)
                }) {
                    Text(text = "Start Service")
                }

                Button(onClick = {
                    Intent(applicationContext, ScreenCaptureService::class.java).also {
                        it.action = ScreenCaptureService.Actions.STOP.toString()
                        startService(it)
                    }
                }) {
                    Text(text = "STOP")
                }
            }
        }
    }

    private fun recognizeTextFromBitmap(bitmap: Bitmap) {
        //InputImage.from
        val inputImage = InputImage.fromBitmap(bitmap, 0)
        val recognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                // Log recognized text
                Log.d("MLKit", "Recognized Text: ${visionText.text}")
                for (block in visionText.textBlocks) {
                    val blockText = block.text
                    val blockBoundingBox = block.boundingBox // Get bounding box
                    val blockCornerPoints = block.cornerPoints // Get corner points
                    Log.d("MLKit", "Block Text: $blockText")
                    Log.d("MLKit", "Bounding Box: $blockBoundingBox")
                    Log.d("MLKit", "Corner Points: ${blockCornerPoints?.joinToString()}")

                }
            }
            .addOnFailureListener { e ->
                // Handle errors
                Log.e("MLKit", "Text recognition failed", e)
            }
    }





}




