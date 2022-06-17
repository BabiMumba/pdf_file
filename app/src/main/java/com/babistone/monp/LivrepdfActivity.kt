package com.babistone.monp

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

            try {
                if (Permission.readAndWriteExternalStorage(this)){
                    downloadFile()
                }
            }catch (e:Exception){
                Toast.makeText(this, "${e.toString()}", Toast.LENGTH_SHORT).show()
            }
           

        }
    }
    private fun downloadFile() {

        val downloadUrl = "http://africau.edu/images/default/sample.pdf"
        val downloadRequest = DownloadManager.Request(Uri.parse(downloadUrl))
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
        downloadRequest.setTitle("Download")
        downloadRequest.setDescription("Download Simple File..")
        downloadRequest.allowScanningByMediaScanner()
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis())
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(downloadRequest)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            downloadFile()
        }
    }


}