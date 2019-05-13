package com.dethdemonaexemple.weatherapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    String SQLRequest;

    public DBHelper( Context context,  String name,int version,String SQLRequest) {
        super(context, name, null, version);
        this.SQLRequest=SQLRequest;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLRequest);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLRequest);
    }
}
