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

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.Random;

public class generatebill extends AppCompatActivity {
    ImageButton vc,save,submit;
    TextView  gv;
    EditText cn,custid,item,amount;
    int count=108;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatebill);
        cn = (EditText)findViewById(R.id.gvcname);
        custid = (EditText)findViewById(R.id.custid);
        item = (EditText)findViewById(R.id.item);
        amount = (EditText)findViewById(R.id.amt);
        gv=(TextView)findViewById(R.id.gvcid);
        vc=(ImageButton)findViewById(R.id.gvfind);
        save=(ImageButton)findViewById(R.id.savebill);
        submit=(ImageButton)findViewById(R.id.submitbill);
        dbhelper1 dbh = new dbhelper1(this);
        vc.setOnClickListener(new View.OnClickListener() {
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
                    gv.setText("ID:"+sb);
                    res.close();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random rand = new Random();
                count = rand.nextInt(1000);
                String billno = custid.getText().toString().substring(0,3)+String.valueOf(count);
                String id = custid.getText().toString();
                String it = item.getText().toString();
                String amt = amount.getText().toString();
                boolean rest = dbh.savebill(id,billno,it,amt);
                if(rest){
                    Toast.makeText(getApplicationContext(),"Saved!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Unable to Save!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"New Bill Created!",Toast.LENGTH_SHORT).show();
                Intent gret = new Intent(generatebill.this,MainActivity.class);
                startActivity(gret);
            }
        });
    }
}