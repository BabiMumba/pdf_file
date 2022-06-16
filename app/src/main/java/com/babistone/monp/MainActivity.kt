package com.babistone.monp

import android.app.DownloadManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var txtv:EditText
    private lateinit var valider:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}