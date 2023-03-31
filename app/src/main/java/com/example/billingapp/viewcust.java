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

public class viewcust extends AppCompatActivity {
    EditText viname;
    ImageButton viewdet;
    TextView det;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcust);
        viname = (EditText)findViewById(R.id.vname);
        viewdet= (ImageButton) findViewById(R.id.viewc2);
        det=(TextView)findViewById(R.id.cdetails);
        dbhelper1 dbh = new dbhelper1(this);
        viewdet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String findname = viname.getText().toString();
                Cursor res = dbh.getdetails(findname);
                if(res.getCount()<=0){
                    Toast.makeText(getApplicationContext(),"User Not found!",Toast.LENGTH_SHORT).show();
                }else{
                    StringBuffer sb = new StringBuffer();
                    //res.moveToFirst();
                    while(res.moveToNext()){
                        sb.append("\t\t\t\t\t Customer Details\n\n");
                        sb.append("Customer ID : "+res.getString(0)+"\n");
                        sb.append("Customer Name : "+res.getString(1)+" \n");
                        sb.append("Phone Number : "+res.getString(2)+" \n");
                        sb.append("Customer Address : "+res.getString(3)+" \n");
                    }
                    det.setText(" "+sb);
                    res.close();
                }
            }
        });

    }
}