package com.example.demolibrary;

import static com.example.demolibrary.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btn,nxtbtn;
//    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
//        imageView = (ImageView) findViewById(R.id.capimgview);

        DemoClass.Toaster(this,"Add:-"+DemoClass.add(12,12));

        System.out.println("Device name:"+Util.getDeviceName());

        btn = (Button) findViewById(id.selectbtn);
        String myVersion = Build.VERSION.RELEASE;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject data = DemoClass.Readfile(getApplicationContext());
                     String n1,n2,n3;
                     n1 = String.valueOf(data.getString("name1"));
                     n2 = String.valueOf(data.getString("name2"));
                     n3 = String.valueOf(data.getString("name3"));
                     if (n1.equals(DemoClass.getDeviceName()))
                         DemoClass.Toaster(getApplicationContext(), "Valide Devicename"+n1);
                     else if (n2.equals(DemoClass.getDeviceName()))
                         DemoClass.Toaster(getApplicationContext(),"Valide Devicename"+n2);
                     else if (n3.equals(DemoClass.getDeviceName()))
                         DemoClass.Toaster(getApplicationContext(),"Valide Devicename"+n3);
                     else
                         DemoClass.Toaster(getApplicationContext(),"Incorrect Devicename");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        nxtbtn = (Button) findViewById(id.nextbtn);
        nxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CameraActivity.class);
                startActivity(intent);
            }
        });
    }
}