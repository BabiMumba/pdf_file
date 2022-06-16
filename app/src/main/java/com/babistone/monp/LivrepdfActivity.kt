package com.babistone.monp

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.net.MalformedURLException
import java.net.URL

class LivrepdfActivity : AppCompatActivity() {
    private var btnDownload: Button? = null
    private var btnView: Button? = null
    private val filepath = "http://africau.edu/images/default/sample.pdf"
    private var url: URL? = null
    private var fileName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initViews()
        setListeners()
    }

    private fun initViews() {

       var  tvFileName = findViewById<TextView>(R.id.tvUrl)
        btnDownload = findViewById(R.id.btnDownload)
        btnView = findViewById(R.id.btnView)
        try {
            url = URL(filepath)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        fileName = url!!.path
        tvFileName.setText(fileName)
    }

    private fun setListeners() {
        btnDownload!!.setOnClickListener { v: View? ->
            val request = DownloadManager.Request(Uri.parse(url.toString() + ""))
            request.setTitle(fileName)
            request.setMimeType("applcation/pdf")
            request.allowScanningByMediaScanner()
            request.setAllowedOverMetered(true)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }
        btnView!!.setOnClickListener { v: View? ->
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/" + fileName
            )
            val uri = FileProvider.getUriForFile(
                this@LivrepdfActivity,
                "com.example.firstproject" + ".provider",
                file
            )
            val i = Intent(Intent.ACTION_VIEW)
            i.setDataAndType(uri, "application/pdf")
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(i)
        }
    }
}