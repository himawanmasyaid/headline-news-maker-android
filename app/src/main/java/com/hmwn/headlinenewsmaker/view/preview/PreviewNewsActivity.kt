package com.hmwn.headlinenewsmaker.view.preview

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hmwn.headlinenewsmaker.ui.theme.FontPrimary
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import com.hmwn.headlinenewsmaker.common.toast
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import com.hmwn.headlinenewsmaker.common.getCurrentDateTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PreviewNewsActivity : ComponentActivity() {

    private var headline = ""
    private var author = ""
    private var datetime = ""
    private lateinit var currentView: View
    private var currentDensity: Float = 0.0f
    private var imageUri : Uri? = null

    companion object {
        const val HEADLINE_ARG = "headline"
        const val AUTHOR_ARG = "author"
        const val DATETIME_ARG = "datetime"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getIntentData()

        setContent {
            MainView()
        }

    }

    private fun getIntentData() {

        headline = intent.getStringExtra(HEADLINE_ARG) ?: ""
        author = intent.getStringExtra(AUTHOR_ARG) ?: ""
        datetime = intent.getStringExtra(DATETIME_ARG) ?: ""

    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainView() {

        var isButtonVisible by remember { mutableStateOf(true) }
        val context = LocalContext.current

        Scaffold(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            bottomBar = {

                if (isButtonVisible) {

                    Box() {
                        Button(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            onClick = {

                                isButtonVisible = false

                                if (imageUri == null) {
                                    toast("Loading ...")
                                }

                                CoroutineScope(Dispatchers.IO).launch {
                                    delay(500) // Simulate loading delay for 2 seconds
                                    withContext(Dispatchers.Main) {
                                        if (imageUri == null) {
                                            convertViewToBitmap(context, currentView, currentDensity)
                                            delay(500)
                                            isButtonVisible = true
                                        } else {
                                            openImageInGallery(imageUri)
                                        }

                                    }
                                }
                            }
                        ) {
                            Text(
                                if (imageUri == null) "Download Image" else "Open Image",
                                fontSize = 16.sp,
                                fontFamily = FontPrimary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
                            )
                        }
                    }

                }

            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    HeadlinePreviewView()
                }

            }
        )
    }

    @Composable
    fun HeadlinePreviewView() {

        currentView = LocalView.current
        currentDensity = LocalDensity.current.density

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // headline
            Text(
                headline,
                fontFamily = FontPrimary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            // author
            Text(
                author,
                fontFamily = FontPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            // date time
            Text(
                datetime,
                fontFamily = FontPrimary,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
            )

        }

    }

    private fun convertViewToBitmap(context: Context, view: View, density: Float) {

        // Get display metrics to calculate scale
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // Calculate the scale factor
        val scale = displayMetrics.density / density

        // Calculate the scaled width and height
        val scaledWidth = (view.width * scale).toInt()
        val scaledHeight = (view.height * scale).toInt()

        // crop button
        val percent = (scaledHeight * 10) / 100
        val customScale: Int = scaledHeight - percent

        // Custom a Bitmap of the scaled size
//        val bitmap = Bitmap.createBitmap(
//            scaledWidth,
//            customScale,
//            Bitmap.Config.ARGB_8888
//        )

        // Create a Bitmap of the scaled size
        val bitmap = Bitmap.createBitmap(
            scaledWidth,
            scaledHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        // Scale and draw the view onto the canvas
        canvas.scale(scale, scale)
        view.draw(canvas)

        // Save the bitmap as an image
        saveImageToDirectory(context, bitmap, headline)

    }

    fun openImageInGallery(uri: Uri?) {

        if (uri != null) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uri, "image/*")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    fun saveImageToDirectory(context: Context, bitmap: Bitmap, fileName: String): Boolean {
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName-${getCurrentDateTime("DD-MM-YYYY HH:mm")}")
        values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/")

        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
        } else {
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }

        if (uri != null) {
            val fos = context.contentResolver.openOutputStream(uri) ?: return false

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.close()

            imageUri = uri

            toast("Download success $fileName")
            return true
        } else {
            toast("Failed Download")
            return false
        }
    }

    private fun setLog(msg: String) {
        Log.e("preview", msg)
    }

}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
//@Composable
//private fun PreviewScreen() {
//    PreviewNewsActivity().MainView()
//}