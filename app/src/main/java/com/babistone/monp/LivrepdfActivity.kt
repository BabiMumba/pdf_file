package com.babistone.monp

import android.app.DownloadManager
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.*

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


    private val filepath = "https://www.esisalama.com/assets/upload/horaire/pdf/HORAIRE%20PREPA.pdf"
    private var url: URL? = null

    private fun downloadFile() {
        try {
            url = URL(filepath)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        val fileName: String

        fileName = url!!.path

        tvUrl.setText(fileName)
        val direct = File(Environment.DIRECTORY_DOWNLOADS, "babistone_livre")
        if (!direct.exists()) {

            direct.mkdirs()

        }


        val downloadRequest = DownloadManager.Request(Uri.parse(url.toString()))
        downloadRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
        downloadRequest.setTitle(fileName)
        downloadRequest.setMimeType("application/pdf")
        downloadRequest.setAllowedOverMetered(true)
        downloadRequest.setDescription("Telechargement...")
        downloadRequest.setVisibleInDownloadsUi(true)
        downloadRequest.allowScanningByMediaScanner()
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        downloadRequest.setDestinationInExternalPublicDir(direct.toString(), "Livre.pdf")
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