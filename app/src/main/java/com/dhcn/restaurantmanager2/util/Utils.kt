package com.dhcn.restaurantmanager2.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.CursorLoader
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.dhcn.restaurantmanager2.App
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


object Utils {
    fun getIsAddDish() = App.getInstant().appModule.isAddDish
    fun setIsAddDish(value: Boolean) {
        App.getInstant().appModule.isAddDish = value
    }

    fun getAppModule() = App.getInstant().appModule

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatLocalDateTime(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return localDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatLocalDateTime1(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm dd-MM-yyyy")
        return localDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toLocalDateTime(value:Long): LocalDateTime {
        val instant = Instant.ofEpochMilli(value)
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToMilliseconds(year: Int, month: Int, day: Int): Long {
        val localDate = LocalDate.of(year, month, day)
        return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToMilliseconds(year: Int, month: Int, day: Int, hour: Int, minute:Int): Long {
        val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertToMilliseconds(localDateTime: LocalDateTime): Long {

        val milliseconds = localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()

        return milliseconds
    }

    fun getFilePathFromUri(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        var cursor: Cursor? = null
        try {
            val contentResolver: ContentResolver = context.contentResolver
            cursor = contentResolver.query(uri, projection, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        } catch (e: Exception) {
            Log.e("FilePathFromUri", "Error getting file path from URI", e)
        } finally {
            cursor?.close()
        }
        return null
    }

    fun getFilePathFromContentUri(context: Context?, uri: Uri?): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursorLoader = CursorLoader(context, uri, projection, null, null, null)
        val cursor = cursorLoader.loadInBackground()
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = cursor.getString(columnIndex)
            }
        } catch (e: java.lang.Exception) {
            Log.e("getFilePathFromContentUri", "Error getting file path from content URI", e)
        } finally {
            cursor?.close()
        }
        return filePath
    }


    fun getContentUriFromFilePath(context: Context, filePath: String): Uri? {
        val contentResolver: ContentResolver = context.contentResolver

        // Define the content values for the file
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DATA, filePath)
            put(MediaStore.Images.Media.MIME_TYPE, "image/*") // Change to the appropriate MIME type if needed
        }

        // Insert the file into the MediaStore
        val contentUri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return contentUri
    }
}