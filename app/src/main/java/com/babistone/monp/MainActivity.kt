package com.babistone.monp

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var tvFileName: TextView? = null
    private var btnDownload: Button? = null
    private  var btnView:android.widget.Button? = null

    private val filepath = "http://africau.edu/images/default/sample.pdf"
    private var url: URL? = null
    private var fileName: String? = null

    private lateinit var txtv:EditText
    private lateinit var valider:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setListeners()

        txtv = findViewById(R.id.lien)
        valider = findViewById(R.id.tlcharger)
        valider.setOnClickListener {
            val url = lien.text.toString()
            val request = DownloadManager.Request(Uri.parse(url))
                .setTitle("fichier")
                .setDescription("telechargement...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
        }
        effacer.setOnClickListener {
            lien.setText("")
        }



    }



    private fun initViews() {
        tvFileName = findViewById(R.id.tvUrl)
        btnDownload = findViewById(R.id.btnDownload)
        btnView = findViewById(R.id.btnView)


        try {
            url = URL(filepath)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        fileName = url!!.path
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1)
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
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName)
            val uri = FileProvider.getUriForFile(this, "com.example.firstproject" + ".provider", file)
            val i = Intent(Intent.ACTION_VIEW)
            i.setDataAndType(uri, "application/pdf")
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
            startActivity(i)
        }

    }
}