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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalView
import com.hmwn.headlinenewsmaker.common.toast
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class PreviewNewsActivity : ComponentActivity() {

    private var headline = ""
    private var author = ""
    private var datetime = ""

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

        val context = LocalContext.current
        val view = LocalView.current
        val density = LocalDensity.current.density

        Scaffold(
            bottomBar = {
                Button(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    onClick = {
//                        convertViewToBitmap(context, view, density)
                    }
                ) {
                    Text(
                        "Download Image",
                        fontSize = 16.sp,
                        fontFamily = FontPrimary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)
                    )
                }
            },
            content = {
                HeadlinePreviewView()
            }
        )
    }

    @Composable
    fun HeadlinePreviewView() {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            // headline
            Text(
                "Baru 3 Hari Menikah, Istri Kabur karena Tahu Gaji Suami Hanya Rp 3,9 Juta",
                fontFamily = FontPrimary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            // author
            Text(
                "Himawan",
                fontFamily = FontPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            // date time
            Text(
                "Kamis, 25 Mei 2024 | 20:00",
                fontFamily = FontPrimary,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
            )

        }

    }

    fun convertViewToBitmap(context: Context, view: View, density: Float) {

        setLog("convertViewToBitmap")

        // Get display metrics to calculate scale
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        // Calculate the scale factor
        val scale = displayMetrics.density / density

        // Calculate the scaled width and height
        val scaledWidth = (view.width * scale).toInt()
        val scaledHeight = (view.height * scale).toInt()

        setLog("scaled width : $scaledWidth")
        setLog("scaled height : $scaledHeight")
        setLog("density : $density")

        // scale frame
//        val customScale = scaledHeight - scaledWidth

//        val persen = scaledHeight * 100 / 20f
//        val customScale = scaledHeight - persen

        // crop button
        val percent = (scaledHeight * 10) / 100
        val customScale: Int = scaledHeight - percent

        setLog("persen : $percent")
        setLog("customScale : $customScale")

        // Create a Bitmap of the scaled size
        val bitmap = Bitmap.createBitmap(
            scaledWidth,
            customScale,
            Bitmap.Config.ARGB_8888
        )

        // Create a Bitmap of the scaled size
//        val bitmap = Bitmap.createBitmap(
//            scaledWidth,
//            scaledHeight,
//            Bitmap.Config.ARGB_8888
//        )

        val canvas = Canvas(bitmap)

        // Scale and draw the view onto the canvas
        canvas.scale(scale, scale)
        view.draw(canvas)

        // Save the bitmap as an image
        val uri = saveBitmapToGallery(context, bitmap)
        setLog("uri image : $uri")
//        val file: File = saveBitmapToGalleryAsFile(bitmap)
//        setLog("file image : $file")
        openImageInGallery(uri)

    }

    fun openImageInGallery(imageUri: Uri?) {

        if (imageUri != null) {
            val intent = Intent(Intent.ACTION_VIEW, imageUri)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(intent)
        } else {
            toast("Gambar Tidak Ditemukan")
        }

    }

    private fun saveBitmapToGalleryAsFile(bitmap: Bitmap): File {
        val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(directory, "image_${System.currentTimeMillis()}.jpg")
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()

        // Add the image to the gallery
        sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))

        return file
    }

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap): Uri? {
        val contentValues = ContentValues().apply {
            put(
                MediaStore.Images.Media.DISPLAY_NAME,
                "quote_image_${System.currentTimeMillis()}.png"
            )
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            uri?.let { imageUri ->
                val outputStream = resolver.openOutputStream(imageUri)
                outputStream?.use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    resolver.update(imageUri, contentValues, null, null)
                }

                return imageUri
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
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