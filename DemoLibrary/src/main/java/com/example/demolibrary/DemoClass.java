package com.example.demolibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class DemoClass {
    public static final int CAMERA_PERM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 100;

    public static int add(int a, int b) {
        return a + b;
    }

    public static void Toaster(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static JSONObject Readfile(Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open("names");
        Scanner scanner = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        return jsondata(builder.toString());
    }

    private static JSONObject jsondata(String toString) {
        StringBuilder builder = new StringBuilder();
        JSONObject data = null;
        try {
            JSONObject object = new JSONObject(toString);
            data = object.getJSONObject("devicename");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}