package com.boryanz.upszakoni.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun openPdfWithExternalReader(context: Context, assetFileName: String) {
    val assetManager = context.assets
    val inputStream = assetManager.open(assetFileName)

    // Create a temporary file to store the asset
    val tempFile = File("${context.cacheDir}/${assetFileName}")
    val outputStream = FileOutputStream(tempFile)

    // Copy the asset to the temporary file
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()

    // Create a URI for the temporary file
    val uri = FileProvider.getUriForFile(context, "com.boryanz.upszakoni.fileprovider", tempFile)
    tempFile.deleteOnExit()
    // Create the intent to open the PDF
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, "application/pdf")
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
    context.startActivity(intent)
}

fun supportExternalPdfReader(context: Context): Boolean {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.parse("file:///sdcard/test.pdf"), "application/pdf")
    val resolveInfo = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return resolveInfo.isNotEmpty()
}
