package com.boryanz.upszakoni.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun openPdfWithExternalReader(context: Context, pdfPath: String) {
  val file = File(pdfPath)
  val uri = FileProvider.getUriForFile(context, "com.boryanz.upszakoni.fileprovider", file)
  val intent = Intent(Intent.ACTION_VIEW).apply {
    setDataAndType(uri, "application/pdf")
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
  }

  context.startActivity(intent)
}

fun supportExternalPdfReader(context: Context): Boolean {
  val intent = Intent(Intent.ACTION_VIEW)
  intent.setDataAndType(Uri.parse("file:///sdcard/test.pdf"), "application/pdf")
  val resolveInfo =
    context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
  return resolveInfo.isNotEmpty()
}
