package com.example.billingapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;


import java.io.File;
import java.io.FileNotFoundException;
public class viewbill extends AppCompatActivity {
    private static final Object PERMISSION_REQUEST_CODE = 200 ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbill);
        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }
        EditText vname,bid;
        ImageButton find,generate,del;
        TextView viewid;
        vname = (EditText)findViewById(R.id.cname);
        bid = (EditText)findViewById(R.id.bcid);
        viewid=(TextView)findViewById(R.id.cid);
        find=(ImageButton)findViewById(R.id.find);
        generate=(ImageButton)findViewById(R.id.deletebill);
        del=(ImageButton)findViewById(R.id.deletebillb);
        dbhelper1 dbh = new dbhelper1(this);

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname = vname.getText().toString();
                Cursor res = dbh.getid(cname);
                if(res.getCount()<=0){
                    Toast.makeText(getApplicationContext(),"User ID Not found!",Toast.LENGTH_SHORT).show();
                }else{
                    StringBuffer sb = new StringBuffer();
                    while(res.moveToNext()){
                        sb.append(res.getString(0)+" ");
                    }
                    viewid.setText("ID:"+sb);
                    res.close();
                }
            }
        });

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = bid.getText().toString();
                Cursor res = dbh.getbills(id);
                String billno,item,date,amt,cid="",cname="";
                int total=0;
                String col1="Customer ID",col2="Bill ID",col3="Particulars",col4="Date of Billing",col5="Amount";
                Table table = new Table(5);
                table.addCell(new Cell().add(new Paragraph(col1)));
                table.addCell(new Cell().add(new Paragraph(col2)));
                table.addCell(new Cell().add(new Paragraph(col3)));
                table.addCell(new Cell().add(new Paragraph(col4)));
                table.addCell(new Cell().add(new Paragraph(col5)));
                if(res.getCount()<=0){
                    Toast.makeText(getApplicationContext(),"Bill Not found!",Toast.LENGTH_SHORT).show();
                }else{
                    while(res.moveToNext()){
                        cid = res.getString(0);
                        billno = res.getString(1);
                        item = res.getString(2);
                        date = res.getString(4);
                        amt = res.getString(3);
                        total+=Integer.parseInt(amt);

                        table.addCell(new Cell().add(new Paragraph(cid)));
                        table.addCell(new Cell().add(new Paragraph(billno)));
                        table.addCell(new Cell().add(new Paragraph(item)));
                        table.addCell(new Cell().add(new Paragraph(date)));
                        table.addCell(new Cell().add(new Paragraph(amt)));
                    }
                    res.close();
                    table.addCell(new Cell().add(new Paragraph(" ")));
                    table.addCell(new Cell().add(new Paragraph(" ")));
                    table.addCell(new Cell().add(new Paragraph(" ")));
                    table.addCell(new Cell().add(new Paragraph("Total")));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(total))));

                    Cursor n = dbh.getname(cid);
                    while(n.moveToNext()) {
                        cname = n.getString(0);
                    }
                }
                try {
                    generatePDF(cname,cid,table);
                    Intent viewc = new Intent(viewbill.this,MainActivity.class);
                    startActivity(viewc);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent delint = new Intent(viewbill.this, deletebill.class);
                startActivity(delint);
            }
        });
    }
    private void generatePDF(String Cname,String Cid,Table table) throws FileNotFoundException {
        String file = Environment.getExternalStorageDirectory()+"/Bill.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));


        Document doc = new Document(pdfDoc);


        String txt = "Bill of service";
        Paragraph head = new Paragraph(txt);
        head.setTextAlignment(TextAlignment.CENTER);
        head.setFontSize(25);
        Color myc = new DeviceRgb(17,57,175);
        head.setFontColor(myc);
        doc.add(head);

        String spt2 = ".";
        Paragraph sp2 = new Paragraph(spt2);
        sp2.setTextAlignment(TextAlignment.LEFT);
        sp2.setFontSize(15);
        Color spc2 = new DeviceRgb(255,255,255);
        sp2.setFontColor(spc2);
        doc.add(sp2);

        String txt1 = "Customer Name: "+Cname;
        Paragraph c1 = new Paragraph(txt1);
        c1.setTextAlignment(TextAlignment.LEFT);
        c1.setFontSize(14);
        Color c1c = new DeviceRgb(0,0,0);
        c1.setFontColor(c1c);
        doc.add(c1);

        String txt2 = "Customer ID: "+Cid;
        Paragraph c2 = new Paragraph(txt2);
        c2.setTextAlignment(TextAlignment.LEFT);
        c2.setFontSize(14);
        Color c2c = new DeviceRgb(0,0,0);
        c2.setFontColor(c2c);
        doc.add(c2);

        String spt1 = ".";
        Paragraph sp1 = new Paragraph(spt1);
        sp1.setTextAlignment(TextAlignment.LEFT);
        sp1.setFontSize(20);
        Color spc1 = new DeviceRgb(255,255,255);
        sp1.setFontColor(spc1);
        doc.add(sp1);

        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);
        table.setFontSize(16);
        Color tabc = new DeviceRgb(0,0,0);
        table.setFontColor(tabc);
        doc.add(table);

        String spt3 = ".";
        Paragraph sp3 = new Paragraph(spt3);
        sp3.setTextAlignment(TextAlignment.LEFT);
        sp3.setFontSize(15);
        Color spc3 = new DeviceRgb(255,255,255);
        sp3.setFontColor(spc3);
        doc.add(sp3);

        String txt4 = "     Billed By:    ";
        Paragraph c4 = new Paragraph(txt4);
        c4.setTextAlignment(TextAlignment.RIGHT);
        c4.setFontSize(10);
        Color c4c = new DeviceRgb(0,0,0);
        c4.setFontColor(c4c);
        doc.add(c4);

        String txt5 = "PRATHIBHA S NADIGER";
        Paragraph c5 = new Paragraph(txt5);
        c5.setTextAlignment(TextAlignment.RIGHT);
        c5.setFontSize(10);
        Color c5c = new DeviceRgb(63,81,181);
        c5.setFontColor(c5c);
        doc.add(c5);

        String txt3 = "© Rakshith S Nadiger 2023";
        Paragraph c3 = new Paragraph(txt3);
        c3.setTextAlignment(TextAlignment.CENTER);
        c3.setFontSize(8);
        Color c3c = new DeviceRgb(0,0,0);
        c3.setFontColor(c3c);
        doc.add(c3);


        doc.close();
        Toast.makeText(viewbill.this, "PDF file generated successfully.", Toast.LENGTH_SHORT).show();
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, (Integer) PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == (Integer)PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}