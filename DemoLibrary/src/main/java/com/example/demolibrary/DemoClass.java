package com.example.demolibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
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

    public static JSONObject Read(Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream inputStream = manager.open("jsonfile");
        Scanner scanner = new Scanner(inputStream);
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
        }
        return json(builder.toString());
    }

    private static JSONObject json(String toString) {
        StringBuilder builder = new StringBuilder();
        JSONObject data = null;
        try {
            JSONObject object = new JSONObject(toString);
            data = object.getJSONObject("androidversion");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static int calculateBrightnessEstimate(android.graphics.Bitmap bitmap, int pixelSpacing) {
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
}