package com.bongoacademy.digitalmoneybag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databasehelper extends SQLiteOpenHelper {
    public static final String digitalmoneybag = "moneybag_database";
    public static final int DATABASE_VERSION = 2;

    public Databasehelper(@Nullable Context context) {
        super(context, digitalmoneybag, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE expense (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT)");
        db.execSQL("CREATE TABLE income (id INTEGER PRIMARY KEY AUTOINCREMENT, amount DOUBLE, reason TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS expense");
        db.execSQL("DROP TABLE IF EXISTS income");

        // after drop create new table
        onCreate(db);
    }

    public void addexpense(double amount, String reason) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("reason", reason);
        db.insert("expense", null, values);
    }

    public double totalexpense() {
        double total = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT amount FROM expense", null);
        while (cursor.moveToNext()) {
            total += cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }




    public void addincome(double amount, String reason) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("amount", amount);
        values.put("reason", reason);
        db.insert("income", null, values);
    }

    public double totalincome() {
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT amount FROM income", null);
        while (cursor.moveToNext()) {
            total += cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }



    public Cursor showallexpensedata(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from expense  ",null);
        return  cursor;

    }

    public void deletebyidforexpense(String id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from expense where id like "+id);



        //  after deleting data id rearrangement
        rearrangeIdsforexpense(db);

        db.close();

    }



    //==================================================================
    public Cursor showallincomedata(){

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from income  ",null);
        return  cursor;

    }

    public void deletebyidforincome(String id){

        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from income where id like "+id);



        //  after deleting data id rearrangement
        rearrangeIdsforincome(db);

        db.close();

    }




    private void rearrangeIdsforexpense(SQLiteDatabase db) {
        //  id set based on new serial
        db.execSQL("UPDATE expense SET id = (SELECT COUNT(*) FROM expense e WHERE e.id <= expense.id)");

        //  reset autoincrement that new item get correct id
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='expense'");
    }


    private void rearrangeIdsforincome(SQLiteDatabase db) {

        //  id set based on new serial
        db.execSQL("UPDATE income SET id = (SELECT COUNT(*) FROM income e WHERE e.id <= income.id)");

        //  reset autoincrement that new item get correct id
        db.execSQL("DELETE FROM sqlite_sequence WHERE name='income'");
    }



}
