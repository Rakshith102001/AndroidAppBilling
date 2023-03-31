package com.example.billingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dbhelper1 extends SQLiteOpenHelper {
    public static final String dbname="billing.db";
    public static final String table="customerdetails";
    public static final String c1="Customer_id";
    public static final String c2=" Customer_Name";
    public static final String c3="Customer_PhoneNumber";
    public static final String c4="Customer_Address";
    public static final String table2="billstab";
    public static final String c5="Customer_id";
    public static final String c6="Bill_Number";
    public static final String c7="Particulars";
    public static final String c8="Amount";
    public static final String c9="BillDate";
    public dbhelper1(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+table+"(Customer_id TEXT primary key,Customer_Name TEXT,Customer_PhoneNumber TEXT unique,Customer_Address TEXT)");
        db.execSQL("create table "+table2+"(Customer_id TEXT,Bill_Number TEXT unique,Particulars TEXT,Amount TEXT,BillDate Text,foreign key(Customer_id) references customerdetails(Customer_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        db.execSQL("DROP TABLE IF EXISTS " + table2);
        onCreate(db);
    }

    public boolean addrow(String id,String name,String phno,String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(c1,id);
        cv.put(c2,name);
        cv.put(c3,phno);
        cv.put(c4,address);
        long res = db.insert(table,null,cv);
        if(res==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getid(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Customer_id from "+table+" where Customer_Name = ?",new  String[]{String.valueOf(name)});
        return c;
    }

    public boolean updaterow(String id,String newid,String name,String phno,String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(c1,newid);
        cv.put(c2,name);
        cv.put(c3,phno);
        cv.put(c4,address);
        long res = db.update(table,cv,"Customer_id = ?",new String[]{String.valueOf(id)});
        if(res==-1){
            return false;
        }else{
            return true;
        }
    }

    public int deleterow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase dbr = this.getReadableDatabase();
        Cursor readc = dbr.rawQuery("select * from "+table2+" where Customer_id = ?",new  String[]{String.valueOf(id)});
        if(readc.getCount()<=0) {
            return db.delete(table, "Customer_id = ?", new String[]{String.valueOf(id)});
        }else{
            return 0;
        }
    }

    public Cursor getdetails(String cname){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from "+table+" where Customer_Name = ?",new  String[]{String.valueOf(cname)});
        return c;
    }

    public boolean savebill(String id,String billno,String item,String amt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        cv.put(c5,id);
        cv.put(c6,billno);
        cv.put(c7,item);
        cv.put(c8,amt);
        cv.put(c9,date);
        long res = db.insert(table2,null,cv);
        if(res==-1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getbills(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from "+table2+" where Customer_id = ?",new  String[]{String.valueOf(id)});
        return c;
    }

    public Cursor getname(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select Customer_Name from "+table+" where Customer_id = ?",new  String[]{String.valueOf(id)});
        return c;
    }

    public int deletebill(String id,String bill){
        SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(table2, "Customer_id=? and Bill_Number=? ", new String[]{String.valueOf(id),String.valueOf(bill)});
    }
}

