package com.example.billingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class custdetails extends AppCompatActivity {
    ImageButton sb;
    EditText n,c,a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custdetails);
        sb=(ImageButton)findViewById(R.id.savebill);
        n=(EditText)findViewById(R.id.name);
        c=(EditText)findViewById(R.id.phno);
        a=(EditText)findViewById(R.id.address);
        dbhelper1 dbh = new dbhelper1(this);
        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = n.getText().toString();
                String address = a.getText().toString();
                String phno = c.getText().toString();
                String id = name.substring(0,3)+phno.substring(0,3);
                boolean res = dbh.addrow(id,name,phno,address);
                if(res){
                    Toast.makeText(getApplicationContext(),"User Added!",Toast.LENGTH_SHORT).show();
                    Intent cret = new Intent(custdetails.this,MainActivity.class);
                    startActivity(cret);
                }else{
                    Toast.makeText(getApplicationContext(),"Unable to add user!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
