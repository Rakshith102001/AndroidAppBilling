package com.example.billingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class deletebill extends AppCompatActivity {
    EditText dbid,dcid;
    ImageButton delb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletebill);
        dbid = (EditText)findViewById(R.id.dbillno);
        dcid = (EditText)findViewById(R.id.dcid);
        delb=(ImageButton)findViewById(R.id.deletebill);
        dbhelper1 dbh = new dbhelper1(this);
        delb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int res = dbh.deletebill(dcid.getText().toString(),dbid.getText().toString());
                if(res<=0){
                    Toast.makeText(getApplicationContext(),"Not Deleted!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Bill Deleted Successfully!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}