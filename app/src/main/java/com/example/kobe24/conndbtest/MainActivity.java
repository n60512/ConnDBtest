package com.example.kobe24.conndbtest;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.*;

import com.squareup.picasso.Picasso;

import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {


    Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread(new Runnable() {
            @Override
            public void run() {

                String chosen = "";
                switch (chosen) {
                    case "drawing":
                        try {
                            URL url = new URL("http://140.122.91.218/thinkingapp/associationrulestest/image/2.png");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            myBitmap = BitmapFactory.decodeStream(input);

                        } catch (IOException e) {
                            // Log exception
                        } finally {
                            ConnDB conn1 = new ConnDB("drawing", "公雞", "user1", myBitmap);
                            myBitmap.recycle();   //  recycle resource
                        }
                        break;
                    case "drawingmult":
                        break;
                    case "oneimage":
                        ConnDB conn1 = new ConnDB("oneimage", "同心心圓", "user1", "34");
                        break;
                    case "association":
                        String[] testArr = {"55", "15", "23"};
                        ConnDB conn2 = new ConnDB("association", "TEST0820", testArr, "user1");
                        break;
                    default:
                        break;

                }
            }
        }).start();


    }
}
