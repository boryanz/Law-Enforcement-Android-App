package com.boryanz.upszakoni.ui.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.boryanz.upszakoni.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle


class PdfViewerActivity : AppCompatActivity() {


    private lateinit var pdfView: PDFView

    companion object {

        const val QUERY_LAW_NAME = "lawName"

        fun createIntent(context: Context, lawName: String): Intent {
            return Intent(context, PdfViewerActivity::class.java).apply {
                putExtra(QUERY_LAW_NAME, lawName)
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
        val lawName = intent.getStringExtra(QUERY_LAW_NAME).orEmpty()
        loadPdf(lawName)
    }

    private fun loadPdf(lawName: String) {
        pdfView.fromAsset(lawName).scrollHandle(DefaultScrollHandle(this)).load()
    }
}