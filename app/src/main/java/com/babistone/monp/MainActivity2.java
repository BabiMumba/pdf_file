package com.babistone.monp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity2 extends AppCompatActivity {

    private TextView tvFileName;
    private Button btnDownload, telch,btnView;

    private String filepath = "http://africau.edu/images/default/sample.pdf";
    private URL url = null;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initViews();
        setListeners();
    }

    private void initViews() {
        tvFileName = findViewById(R.id.tvUrl);
        btnDownload = findViewById(R.id.btnDownload);
        btnView = findViewById(R.id.btnView);
        telch = findViewById(R.id.telc);


        try {
            url = new URL(filepath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        fileName = url.getPath();
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
        tvFileName.setText(fileName);
    }

    private void setListeners() {

        btnDownload.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission error","You have permission");
                } else {

                    Log.e("Permission error","You have asked for permission");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
            else { //you dont need to worry about these stuff below api level 23
                Log.e("Permission error","You already have the permission");
                Toast.makeText(this, "erreur", Toast.LENGTH_SHORT).show();
            }

        });

        telch.setOnClickListener(v -> {

            try {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url + ""));
                request.setTitle(fileName);
                request.setDescription("telechargement...");
                request.setMimeType("applcation/pdf");
                request.allowScanningByMediaScanner();
                request.setAllowedOverMetered(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
            }catch (Exception e ){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    btnView.setOnClickListener(v -> {
        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+fileName);
        Uri uri= FileProvider.getUriForFile(MainActivity2.this,"com.example.firstproject"+".provider",file);
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(uri,"application/pdf");
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(i);

    });
    }

}