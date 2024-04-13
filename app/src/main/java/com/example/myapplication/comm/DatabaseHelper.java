package com.example.myapplication.comm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "finalMovie.db";
    public static final String TABLE_NAME = "movies_table";
    public static final String TABLE_NAME2 = "pixel_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "YEAR";
    public static final String COL_4 = "PLOT";
    public static final String COL_5 = "RUNTIME";
    public int avg = 0;
    public int shortest = 0;
    public int longest = 0;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID TEXT, TITLE TEXT, YEAR TEXT, PLOT TEXT, RUNTIME INT)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME2 + "(ID TEXT, TITLE TEXT, height TEXT, weight TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String movieId, String title, String year, String plot, int runtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, movieId);
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, year);
        contentValues.put(COL_4, plot);
        contentValues.put(COL_5, runtime);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean insertData1(int myid, String title, String height, String weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", myid);
        contentValues.put("TITLE", title);
        contentValues.put("height", height);
        contentValues.put("weight", weight);
        long result = db.insert(TABLE_NAME2, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }

    public Integer deleteData1(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, "ID=?", new String[]{id});
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT* FROM " + TABLE_NAME, null);
        return res;
    }

    public Cursor getAllData2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT* FROM " + TABLE_NAME2, null);
        return res;
    }
}