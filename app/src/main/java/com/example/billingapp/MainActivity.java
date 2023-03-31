package com.example.billingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ImageButton ac,ec,gb,vb,dc,vc;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ac=(ImageButton)findViewById(R.id.add);
        ec=(ImageButton)findViewById(R.id.edit);
        gb=(ImageButton)findViewById(R.id.generate);
        vb=(ImageButton)findViewById(R.id.view);
        dc=(ImageButton)findViewById(R.id.delete);
        vc=(ImageButton)findViewById(R.id.viewc);
        ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addintent = new Intent(MainActivity.this, custdetails.class);
                startActivity(addintent);
            }
        });
        ec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editintent = new Intent(MainActivity.this, editcustomer.class);
                startActivity(editintent);
            }
        });
        vc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewc = new Intent(MainActivity.this, viewcust.class);
                startActivity(viewc);
            }
        });

        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent generateb = new Intent(MainActivity.this, generatebill.class);
                startActivity(generateb);
            }
        });
        vb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewb = new Intent(MainActivity.this, viewbill.class);
                startActivity(viewb);
            }
        });
        dc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteintent = new Intent(MainActivity.this, deletecust.class);
                startActivity(deleteintent);
            }
        });
    }
}