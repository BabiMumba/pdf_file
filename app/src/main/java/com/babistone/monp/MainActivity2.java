package com.babistone.monp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity2 extends AppCompatActivity {
    final Context c = this;
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


            /*
            if(PermissionCheck.readAndWriteExternalStorage(context)){
    //Your read write code.
}
             */
            if (PermissionCheck.readAndWriteExternalStorage(this)){

                String myurl = "http://africau.edu/images/default/sample.pdf";
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myurl));
                request.setTitle("babtest");
                request.setDescription("telechargement");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String filename = URLUtil.guessFileName(myurl, null, MimeTypeMap.getFileExtensionFromUrl(myurl));
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
               
            }

        });

        telch.setOnClickListener(v -> {
            String myurl = "http://africau.edu/images/default/sample.pdf";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myurl));
            request.setTitle("babtest");
            request.setDescription("telechargement");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            String filename = URLUtil.guessFileName(myurl, null, MimeTypeMap.getFileExtensionFromUrl(myurl));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
            DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);


        });



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //you have the permission now.

            String myurl = "http://africau.edu/images/default/sample.pdf";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myurl));
            request.setTitle("babtest");
            request.setDescription("telechargement");
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            String filename = URLUtil.guessFileName(myurl, null, MimeTypeMap.getFileExtensionFromUrl(myurl));
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
            DownloadManager manager = (DownloadManager) c.getSystemService(Context.DOWNLOAD_SERVICE);
        }
    }

}