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

public class editcustomer extends AppCompatActivity {
    EditText cn,upid,upname,upadd,upphno;
    TextView vid;
    ImageButton saveb,findb;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editcustomer);

        cn = (EditText)findViewById(R.id.cname);
        upid = (EditText)findViewById(R.id.uid);
        upname = (EditText)findViewById(R.id.uname);
        upadd = (EditText)findViewById(R.id.uadd);
        upphno = (EditText)findViewById(R.id.uphno);
        vid=(TextView)findViewById(R.id.cid);
        saveb=(ImageButton)findViewById(R.id.save);
        findb=(ImageButton)findViewById(R.id.find);
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
                        sb.append(res.getString(0)+" ");
                    }
                    vid.setText("ID:"+sb);
                    res.close();
                }
            }
        });

        saveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = upid.getText().toString();
                String name = upname.getText().toString();
                String phno = upphno.getText().toString();
                String add = upadd.getText().toString();
                String newid = name.substring(0,3)+phno.substring(0,3);
                boolean res = dbh.updaterow(id,newid,name,phno,add);
                if(res){
                    Toast.makeText(getApplicationContext(),"User details Updated & User ID changed!",Toast.LENGTH_SHORT).show();
                    Intent ret = new Intent(editcustomer.this, MainActivity.class);
                    startActivity(ret);
                }else{
                    Toast.makeText(getApplicationContext(),"Update Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}