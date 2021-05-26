package com.example.techapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminDatabase extends SQLiteOpenHelper {

    public AdminDatabase(@Nullable Context context) {
        super(context,"ProductDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Product(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT UNIQUE,image blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table IF EXISTS Product");
    }

    //insert product name and image in databse
    public boolean addData(String name_price,byte[] img){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name_price);
        contentValues.put("image",img);
        long result=db.insert("Product",null,contentValues);
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }
    //delete name and product
    public Boolean deletedata(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Product where name= ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Product", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

    //get database data
    public Cursor getData(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from Product",null);
        while(cursor!=null){
            cursor.moveToNext();
        }
        return cursor;
    }
}
