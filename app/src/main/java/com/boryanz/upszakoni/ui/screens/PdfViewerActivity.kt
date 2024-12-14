package com.boryanz.upszakoni.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.boryanz.upszakoni.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle


class PdfViewerActivity : AppCompatActivity() {


    private lateinit var pdfView: PDFView

    companion object {

        const val BUNDLE_LAW_TITLE = "lawTitle"
        const val BUNDLE_PAGES_TO_LOAD = "pagesToLoad"
        const val BUNDLE_IS_DARK_MODE = "isDarkMode"

        fun createIntent(context: Context, bundle: Bundle? = null): Intent {
            return Intent(context, PdfViewerActivity::class.java).apply {
                putExtras(bundle ?: bundleOf())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pdf_viewer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        pdfView = findViewById(R.id.pdfView)
        val data = intent.extras
        val lawName = data?.getString(BUNDLE_LAW_TITLE).orEmpty()
        val pagesToLoad = data?.getIntArray(BUNDLE_PAGES_TO_LOAD) ?: intArrayOf()
        val isInDarkMode = data?.getBoolean(BUNDLE_IS_DARK_MODE, false) ?: false
        loadPdf(lawName, pagesToLoad, isInDarkMode)
    }

    private fun loadPdf(lawName: String, pagesToLoad: IntArray, isInDarkMode: Boolean) {
        pdfView.fromAsset(lawName).apply {
            if (pagesToLoad.isNotEmpty()) {
                pages(*pagesToLoad)
            }
            nightMode(isInDarkMode)
            scrollHandle(DefaultScrollHandle(this@PdfViewerActivity))
            load()
        }
    }
}