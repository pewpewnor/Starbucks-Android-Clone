package com.example.starbucks2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {
    public final static String DB_NAME = "Starbucks2.db";
    public final static int DB_VERSION = 1;

    public final static String PRODUCTS = "Products";
    public final static String ORDERS = "Orders";
    public final static String ID = "Id";
    public final static String NAME = "Name";
    public final static String PRICE = "Price";
    public final static String DESCRIPTION = "Description";
    public final static String IMGID = "ImgId";

    public final static String ITEMS = "Items";
    public final static String TOTALPRICE = "TotalPrice";




    private static DBOpenHelper db = null;

    private DBOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBOpenHelper getInstance(Context context) {
        if (db == null) {
            db = new DBOpenHelper(context);
        }
        return db;
    }

    public void del() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ PRODUCTS);
        db.close();
        this.db = null;
    }

    public void delOrderHistory() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ ORDERS);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PRODUCTS + "(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                NAME + " TEXT NOT NULL," +
                PRICE + " INTEGER NOT NULL," +
                DESCRIPTION + " TEXT NOT NULL," +
                IMGID + " INTEGER NOT NULL)");

        Log.d("hey", "bruh1");

        db.execSQL("CREATE TABLE " + ORDERS + "(" +
                ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                ITEMS + " TEXT NOT NULL," +
                TOTALPRICE + " INTEGER NOT NULL)");

        Log.d("hey", "bruh");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
