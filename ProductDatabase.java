package com.example.techapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProductDatabase extends SQLiteOpenHelper {

    public ProductDatabase(@Nullable Context context) {
        super(context, "CartData.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table ProductTable(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT UNIQUE,image BLOB,description TEXT,price TEXT)");
        db.execSQL("create table PriceStore(name TEXT UNIQUE,price TEXT,quantity INTEGER)");
        db.execSQL("create table Deleivery(name TEXT UNIQUE,price TEXT,quantity INTEGER,singleprice INTEGER,charges INTEGER,total_amount INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists ProductTable");
        onCreate(db);
    }
    public Boolean insertProduct(String prod_name, byte[] imgShow,String prod_description,String prod_price)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", prod_name);
        contentValues.put("price",prod_price);
        contentValues.put("image", imgShow);
        contentValues.put("description", prod_description);
        long result=DB.insert("ProductTable", null, contentValues);
        insertRecords();
        if(result==-1){
            return false;
        }
        else{
            return true;
        }
    }

    //insert in PriceStore table
    public void insertRecords(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from ProductTable",null);
        ContentValues contentValues = new ContentValues();
        while(cursor.moveToNext()){
            contentValues.put("name", cursor.getString(1));
            contentValues.put("price",cursor.getString(4));
            contentValues.put("quantity",1);
            DB.insert("PriceStore",null, contentValues);
        }
    }

    //Update records in PriceStore table
    public Boolean updateProduct(String name,String priceValue,int newQuantity)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("price", priceValue);
        contentValues.put("quantity", newQuantity);
        Cursor cursor = DB.rawQuery("Select * from PriceStore where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("PriceStore", contentValues, "name=?", new String[]{name});
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

    //Get from PriceStore table
    public Cursor getData ()
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from PriceStore",null);
        return cursor;
    }

    public Boolean deletedata (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        long result = DB.delete("ProductTable","name=?",new String[]{name});
        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }
    public void deleteDuplicate(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("ProductTable","name<>?",new String[]{"select DISTINCT from ProductTable"});
    }

    public Cursor getProductData ()
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from ProductTable",null);
        return cursor;
    }

    //insert records in deleivery table
    public void insertDeleivery(String prod_name,String prod_price,int prod_quantity,int prod_singleItemPrice,int deleivery_charges,int total_to_pay){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", prod_name);
        contentValues.put("price",prod_price);
        contentValues.put("quantity",prod_quantity);
        contentValues.put("singleprice",prod_singleItemPrice);
        contentValues.put("charges",deleivery_charges);
        contentValues.put("total_amount",total_to_pay);
        DB.insert("Deleivery",null, contentValues);
    }
    //get data from deleivery table
    public Cursor getDataFromDeleivery()
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        Cursor cursor = DB.rawQuery("select * from Deleivery",null);
        return cursor;
    }
    //delete data from deleivery table
    public void deletedataFromDeleivery(String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("Deleivery","name=?",new String[]{name});
    }
}
