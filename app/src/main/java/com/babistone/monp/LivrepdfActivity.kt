package com.babistone.monp

import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.net.MalformedURLException
import java.net.URL

class LivrepdfActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        telc.setOnClickListener {
            try {
                if (Permission.readAndWriteExternalStorage(this)){
                    downloadFile()
                }
            }catch (e: Exception){
                Toast.makeText(this, "${e.toString()}", Toast.LENGTH_SHORT).show()

            }
        }
    }


    private val filepath = "http://africau.edu/images/default/sample.pdf"
    private var url: URL? = null

    private fun downloadFile() {
        try {
            url = URL(filepath)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        val fileName: String
        fileName = url!!.path




        val downloadRequest = DownloadManager.Request(Uri.parse(url.toString()))
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
        downloadRequest.setTitle("livre")
        downloadRequest.setMimeType("applcation/pdf")
        downloadRequest.setAllowedOverMetered(true)
        downloadRequest.setDescription("Telechargement...")
        downloadRequest.allowScanningByMediaScanner()
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
        downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
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