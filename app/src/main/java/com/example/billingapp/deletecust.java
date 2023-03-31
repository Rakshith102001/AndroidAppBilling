package com.example.billingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class deletecust extends AppCompatActivity {
    ImageButton findb,deleteb;
    EditText cn,did;
    TextView vid;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletecust);
        cn = (EditText)findViewById(R.id.dcname);
        did = (EditText)findViewById(R.id.did);
        vid=(TextView)findViewById(R.id.dcid);
        findb=(ImageButton)findViewById(R.id.dfind);
        deleteb=(ImageButton)findViewById(R.id.delb);
        dbhelper1 dbh = new dbhelper1(this);

        findb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname = cn.getText().toString();
                Cursor res = dbh.getid(cname);
                if(res.getCount()<=0){
                    Toast.makeText(getApplicationContext(),"User ID Not found!",Toast.LENGTH_SHORT).show();
                }else{
                    StringBuffer sb = new StringBuffer();
                    while(res.moveToNext()){
                        sb.append(res.getString(0)+",");
                    }
                    vid.setText("ID:"+sb);
                    res.close();
                }
            }
        });
        deleteb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = did.getText().toString();
                int res=dbh.deleterow(id);
                if(res>0){
                    Toast.makeText(getApplicationContext(),"User Deleted!",Toast.LENGTH_SHORT).show();
                    Intent dret = new Intent(deletecust.this,MainActivity.class);
                    startActivity(dret);
                }else{
                    Toast.makeText(getApplicationContext(),"Deletion Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}