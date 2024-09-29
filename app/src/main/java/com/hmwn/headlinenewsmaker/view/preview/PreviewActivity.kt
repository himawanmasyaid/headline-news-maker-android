package com.hmwn.headlinenewsmaker.view.preview

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hmwn.headlinenewsmaker.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import android.graphics.Color;
import com.hmwn.headlinenewsmaker.common.getCurrentDateTime
import com.hmwn.headlinenewsmaker.common.toast
import com.hmwn.headlinenewsmaker.data.Constants.PATH_DEFAULT
import com.hmwn.headlinenewsmaker.databinding.ActivityPreviewBinding
import com.hmwn.headlinenewsmaker.databinding.ViewTemplateLandscapeCnnBinding


class PreviewActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPreviewBinding.inflate(layoutInflater)
    }

    companion object {
        const val HEADLINE_ARG = "headline"
        const val IMAGE_ARG = "image"
    }

    val headline by lazy {
        intent.getStringExtra(HEADLINE_ARG) ?: ""
    }

    val byteArray by lazy {
        intent.getByteArrayExtra(IMAGE_ARG)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initListener()

    }

    private fun initView() {

        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

        with(binding) {
            ivPreview.setImageBitmap(bitmap)
        }

    }

    private fun initListener() {

        with(binding) {

            btnBack.setOnClickListener {
                finish()
            }

            btnDownload.setOnClickListener {
                val bitmap = getBitmapFromUiView(ivPreview)
                saveBitmapImage(bitmap, headline)
            }

        }

    }

    /**Get Bitmap from any UI View
     * @param view any UI view to get Bitmap of
     * @return returnedBitmap the bitmap of the required UI View
     */
    private fun getBitmapFromUiView(view: View): Bitmap {
        //Define a bitmap with the same size as the view
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        //Bind a canvas to it
        val canvas: Canvas = Canvas(returnedBitmap)
        //Get the view's background
        val bgDrawable = view.background
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas)
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE)
        }
        // draw the view on the canvas
        view.draw(canvas)

        //return the bitmap
        return returnedBitmap
    }

    /**Save Bitmap To Gallery
     * @param bitmap The bitmap to be saved in Storage/Gallery
     */
    private fun saveBitmapImage(bitmap: Bitmap, fileName: String) {
        val timestamp = System.currentTimeMillis()

        //Tell the media scanner about the new file so that it is immediately available to the user.
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
            values.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                "Pictures/" + PATH_DEFAULT
            )
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                try {
                    val outputStream = contentResolver.openOutputStream(uri)
                    if (outputStream != null) {
                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                            outputStream.close()
                        } catch (e: Exception) {
                            toast(getString(R.string.download_failed))
                        }
                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    contentResolver.update(uri, values, null, null)

                    toast(getString(R.string.download_success))
                } catch (e: Exception) {
                    toast(getString(R.string.download_failed))
                }
            }
        } else {
            val imageFileFolder = File(
                Environment.getExternalStorageDirectory()
                    .toString() + '/' + PATH_DEFAULT
            )
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdirs()
            }
            val mImageName = "$fileName-${getCurrentDateTime("DD-MM-YYYY HH:mm")}.png"

            val imageFile = File(imageFileFolder, mImageName)
            try {
                val outputStream: OutputStream = FileOutputStream(imageFile)
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    toast(getString(R.string.download_failed))
                }
                values.put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

                toast(getString(R.string.download_success))
            } catch (e: Exception) {
                toast(getString(R.string.download_failed))
            }
        }
    }

}