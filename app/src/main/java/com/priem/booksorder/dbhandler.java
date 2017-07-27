package com.priem.booksorder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adnan on 7/17/2017.
 */

public class dbhandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "adnan.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String Column_ID = "_id";
    public static final String Column_Productname = "title";
    public static final String Column_qty = "qty";

    public dbhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "( " + Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_Productname + " TEXT " + ");";
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXIST" + TABLE_PRODUCTS);
        onCreate(db);
    }

    //INSERTING CODE
    public void addProduct(Product p1) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Column_Productname, p1.getTitle());
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    public String databasetostring() {

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("title")) != null) {
                dbString += c.getString(c.getColumnIndex("title"));
                dbString += "\n";
            }
            c.moveToNext();

        }
        db.close();

        return dbString;
    }


}
