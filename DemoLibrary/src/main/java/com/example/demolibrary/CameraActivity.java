package com.example.demolibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class CameraActivity extends AppCompatActivity {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 100;
    Button selectmenubtn;
    ImageView imgv;
    AlertDialog.Builder builder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        builder = new AlertDialog.Builder(this);
        imgv = (ImageView) findViewById(R.id.capimgv);
        selectmenubtn = (Button)  findViewById(R.id.selectbtn);
        selectmenubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Select Option")
                        .setCancelable(true)
                        .setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                askCameraPermission();
                            }
                        })
                        .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openGallery();
                            }
                        })
                        .show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA} , CAMERA_PERM_CODE);
        }
        else {
            openCamera();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else {
                Toast.makeText(this, "Please grant Camera Permission........", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void openCamera() {
        Intent camer = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camer, CAMERA_REQUEST_CODE);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap image = null;
        if (requestCode == CAMERA_REQUEST_CODE){
                Bitmap image = (Bitmap) data.getExtras().get("data");
                imgv.setImageBitmap(image);
            Toast.makeText(this, "Brightness value:-"+calculateBrightnessEstimate(image,1), Toast.LENGTH_LONG).show();
        }
        else if (requestCode == GALLERY_REQUEST_CODE){
//            Bitmap image1 = (Bitmap) data.getExtras().get("data");
//            imgv.setImageBitmap(image1);
//                imgv.setImageURI(data.getData());
//            Toast.makeText(this, "Brightness value:-"+calculateBrightnessEstimate(image1,1), Toast.LENGTH_LONG).show();
            Uri uri = data.getData();
            imgv.setImageURI(uri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Toast.makeText(this, "Brightness value:-"+calculateBrightnessEstimate(bitmap,1), Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public int calculateBrightnessEstimate(android.graphics.Bitmap bitmap, int pixelSpacing) {
        int R = 0; int G = 0; int B = 0;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int n = 0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < pixels.length; i += pixelSpacing) {
            int color = pixels[i];
            R += Color.red(color);
            G += Color.green(color);
            B += Color.blue(color);
            n++;
        }
        return (R + B + G) / (n * 3);
    }

//    public int calculateBrightnessEstimate(Uri uri, int pixelSpacing) {
//        int R = 0; int G = 0; int B = 0;
//        int height = uri.getHeight();
//        int width = uri.getWidth();
//        int n = 0;
//        int[] pixels = new int[width * height];
//        uri.getPixels(pixels, 0, width, 0, 0, width, height);
//        for (int i = 0; i < pixels.length; i += pixelSpacing) {
//            int color = pixels[i];
//            R += Color.red(color);
//            G += Color.green(color);
//            B += Color.blue(color);
//            n++;
//        }
//        return (R + B + G) / (n * 3);
//    }
}