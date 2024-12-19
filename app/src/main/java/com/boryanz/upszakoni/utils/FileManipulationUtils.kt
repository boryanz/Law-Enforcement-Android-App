import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.github.barteksc.pdfviewer.util.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun openPdfFromAssets(context: Context, assetFileName: String) {
    val assetManager = context.assets
    val inputStream = assetManager.open(assetFileName)

    // Create a temporary file to store the asset
    val tempFile = File("${context.cacheDir}/${assetFileName}.pdf")
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
