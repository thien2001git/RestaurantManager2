package com.dhcn.restaurantmanager2.util

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ImageUtils {
    companion object {
        suspend fun saveImageFromContentUri(context: Context, contentUri: Uri): File? {
            return withContext(Dispatchers.IO) {
                try {
                    val inputStream: InputStream? = context.contentResolver.openInputStream(contentUri)
                    val bitmap: Bitmap? = BitmapFactory.decodeStream(inputStream)

                    if (bitmap != null) {
                        val file = createImageFile(context)
                        saveBitmapToFile(bitmap, file)
                        return@withContext file
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                return@withContext null
            }
        }

        private fun createImageFile(context: Context): File {
            // Create an image file name
            val timeStamp: String = System.currentTimeMillis().toString()
            val imageFileName = "JPEG_${timeStamp}_"
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
            )
        }

        private fun saveBitmapToFile(bitmap: Bitmap, file: File) {
            var outputStream: OutputStream? = null

            try {
                outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    outputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
