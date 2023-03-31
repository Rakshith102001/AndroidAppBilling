package com.example.billingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;

public class Splashscreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread  t1 = new Thread(){
            public void run(){
                try {
                    sleep(3000);
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    Intent intent1 = new Intent(Splashscreen.this, MainActivity.class);
                    startActivity(intent1);
                }
            }
        };t1.start();
    }
}