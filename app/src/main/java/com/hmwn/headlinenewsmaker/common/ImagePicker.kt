package com.hmwn.headlinenewsmaker.common

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Parcelable
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.hmwn.headlinenewsmaker.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ImagePicker(private val context: Context, private val authority: String) {

    companion object {
        private const val DEFAULT_MIN_WIDTH_QUALITY = 1000
        private const val TAG = "ImagePicker"
        private const val TEMP_IMAGE_NAME = "tempImage"
    }


    private var minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY


    fun getPickImageIntent(context: Context): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra("return-data", true)
        takePhotoIntent.putExtra(
            MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
                context,
                authority, getTempFile()
            )
        )
        takePhotoIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        intentList = addIntentsToList(context, intentList, pickIntent)
        intentList = addIntentsToList(context, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeAt(intentList.size - 1),
                context.getString(R.string.choose_or_take_photo)
            )
            chooserIntent!!.putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    private fun addIntentsToList(
        context: Context,
        list: MutableList<Intent>,
        intent: Intent
    ): MutableList<Intent> {
        val packageManager = context.packageManager
        val resInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        } else {
            packageManager.queryIntentActivities(intent, 0)
        }
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
            Log.d(TAG, "Intent: " + intent.action + " package: " + packageName)
        }
        return list
    }


    fun getBitmapImageFromUri(imageReturnedIntent: Uri?): Bitmap? {
        var bm: Bitmap?
        val imageFile = getTempFile()

        val isCamera =
            imageReturnedIntent == null || imageReturnedIntent.toString()
                .contains(imageFile.toString())
        val selectedImage = if (isCamera) {
            // from camera
            FileProvider.getUriForFile(context, authority, getTempFile())
        } else {
            // from gallery
            imageReturnedIntent
        }

        bm = getImageResized(selectedImage!!)
        val rotation = getRotation(context, selectedImage, isCamera)
        bm = rotate(bm, rotation)

        return bm
    }


    private fun getTempFile(): File {
        val imageFile = File(context.externalCacheDir, TEMP_IMAGE_NAME)
        imageFile.parentFile?.mkdirs()
        return imageFile
    }

    private fun getImageFromResult(imageReturnedIntent: Uri?): Bitmap? {
        var bm: Bitmap?
        val imageFile = getTempFile()

        val isCamera =
            imageReturnedIntent == null || imageReturnedIntent.toString()
                .contains(imageFile.toString())
        val selectedImage = if (isCamera) {
            // from camera
            FileProvider.getUriForFile(context, authority, getTempFile())
        } else {
            // from gallery
            imageReturnedIntent
        }

        bm = getImageResized(selectedImage!!)
        val rotation = getRotation(context, selectedImage, isCamera)
        bm = rotate(bm, rotation)

        return bm
    }

    fun getFileFromResult(imageReturnedIntent: Uri?): File? {
        val bm = getImageFromResult(imageReturnedIntent)

        return try {
            convertImageToFile(bm, System.currentTimeMillis().toString())
        } catch (e: Exception) {
            null
        }
    }

    private fun decodeBitmap(theUri: Uri, sampleSize: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inSampleSize = sampleSize

        var fileDescriptor: AssetFileDescriptor? = null
        try {
            fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri, "r")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
            fileDescriptor!!.fileDescriptor, null, options
        )

        Log.d(
            TAG, options.inSampleSize.toString() + " sample method bitmap ... " +
                    actuallyUsableBitmap.width + " " + actuallyUsableBitmap.height
        )

        return actuallyUsableBitmap
    }

    /**
     * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
     */
    private fun getImageResized(selectedImage: Uri): Bitmap {
        var bm: Bitmap?
        val sampleSizes = intArrayOf(5, 3, 2, 1)
        var i = 0
        do {
            bm = decodeBitmap(selectedImage, sampleSizes[i])
            Log.d(TAG, "resizer: new bitmap width = " + bm.width)
            i++
        } while (bm!!.width < minWidthQuality && i < sampleSizes.size)
        return bm
    }


    private fun getRotation(context: Context, imageUri: Uri, isCamera: Boolean): Int {
        return if (isCamera) {
            getRotationFromCamera(context, imageUri)
        } else {
            getRotationFromGallery(context, imageUri)
        }
    }

    private fun getRotationFromCamera(context: Context, imageFile: Uri): Int {
        var rotate = 0
        try {
            context.contentResolver.notifyChange(imageFile, null)
            val inputStream = context.contentResolver.openInputStream(imageFile)
            val exif = ExifInterface(inputStream!!)

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            rotate = when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                else -> 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rotate
    }

    private fun getRotationFromGallery(context: Context, imageUri: Uri): Int {
        var result = 0
        val columns = arrayOf(MediaStore.Images.Media.ORIENTATION)
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(imageUri, columns, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    val orientationColumnIndex = cursor.getColumnIndex(columns[0])
                    result = cursor.getInt(orientationColumnIndex)
                }
            }
        } catch (e: Exception) {
            //Do nothing
        } finally {
            cursor?.close()
        }
        return result
    }


    private fun rotate(bm: Bitmap, rotation: Int): Bitmap? {
        if (rotation != 0) {
            val matrix = Matrix()
            matrix.postRotate(rotation.toFloat())
            return Bitmap.createBitmap(bm, 0, 0, bm.width, bm.height, matrix, true)
        }
        return bm
    }

    private fun getPathFromURI(context: Context, uri: Uri): String {
        var realPath = String()
        uri.path?.let { path ->

            val databaseUri: Uri
            val selection: String?
            val selectionArgs: Array<String>?
            if (path.contains("/document/image:")) { // files selected from "Documents"
                databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                selection = "_id=?"
                selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
            } else { // files selected from all other sources, especially on Samsung devices
                databaseUri = uri
                selection = null
                selectionArgs = null
            }
            try {
                val column = "_data"
                val projection = arrayOf(column)
                val cursor = context.contentResolver.query(
                    databaseUri,
                    projection,
                    selection,
                    selectionArgs,
                    null
                )
                cursor?.let {
                    if (it.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndexOrThrow(column)
                        realPath = cursor.getString(columnIndex)
                    }
                    cursor.close()
                }
            } catch (e: Exception) {
                println(e)
            }
        }
        return realPath
    }

    private fun getImageUriFromBitmap(
        context: Context, bitmap: Bitmap?, name: String
    ): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        Log.d("before compressBitmap", "Size: " + bytes.toByteArray().size / 1024 + " kb")
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, name, null)
        return Uri.parse(path.toString())
    }

    fun convertImageToFile(bitmapImage: Bitmap?, name: String): File {
        val imageUri = getImageUriFromBitmap(context, bitmapImage, name)
        val resizeAndCompressImage =
            resizeAndCompressImage(context, getPathFromURI(context, imageUri), name)
        return File(resizeAndCompressImage)
    }

    private fun resizeAndCompressImage(
        context: Context,
        filePath: String,
        fileName: String
    ): String {
        val maxImageSize = 4096 * 1024 // max final file size in kilobytes

        // First decode with inJustDecodeBounds=true to check dimensions of image
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)

        // Calculate inSampleSize(First we are going to resize the image to 800x800 image, in order to not have a big but very low quality image.
        //resizing the image will already reduce the file size, but after resizing we will check the file size and start to compress image
        options.inSampleSize = calculateInSampleSize(options)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        options.inPreferredConfig = Bitmap.Config.ARGB_8888

        val bmpPic = BitmapFactory.decodeFile(filePath, options)


        var compressQuality = 100 // quality decreasing by 5 every loop.
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            Log.d("compressBitmap", "Quality: $compressQuality")
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
            Log.d("compressBitmap", "Size: " + streamLength / 1024 + " kb")
        } while (streamLength >= maxImageSize)

        try {
            //save the resized and compressed file to disk cache
            Log.d("compressBitmap", "cacheDir: " + context.cacheDir)
            val bmpFile = FileOutputStream(context.cacheDir.toString() + fileName)
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpFile)
            bmpFile.flush()
            bmpFile.close()
        } catch (e: Exception) {
            Log.e("compressBitmap", "Error on saving file")
        }

        //return the path of resized and compressed file
        return context.cacheDir.toString() + fileName
    }


    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int = 2048,
        reqHeight: Int = 2048
    ): Int {
        val debugTag = "MemoryInformation"
        val height = options.outHeight
        val width = options.outWidth
        Log.d(debugTag, "image height: $height---image width: $width")
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2
            }
        }
        Log.d(debugTag, "inSampleSize: $inSampleSize")
        return inSampleSize
    }

    fun deleteImage(file: File) {
        try {
            if (file.exists()) file.delete()
        } catch (e: java.lang.Exception) {
            Log.e("deleteFile", "Exception while deleting file ${file.path} " + e.message)
        }
    }

}