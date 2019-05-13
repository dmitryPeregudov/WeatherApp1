package com.dethdemonaexemple.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBPresenter {
    Context context;
    String TAG="MyLog";
    String dbPath,nameDB;




    public DBPresenter(Context context) {
        this.context = context;
        dbPath="/data/data/com.dethdemonaexemple.weatherapp/databases/weatherData.db";
        nameDB="weatherData.db";
    }


    Cordinates getCoordinates() throws InterruptedException {

        SQLiteDatabase database;
        Cursor cursor;
        DBHelper dbHelper;

        database=SQLiteDatabase.openOrCreateDatabase(dbPath,null);
        int version=database.getVersion();
        database.close();

        dbHelper=new DBHelper(context,nameDB,version,null);

        try {
            database=dbHelper.getWritableDatabase();
        }catch (SQLException e){database=dbHelper.getReadableDatabase();}

        cursor=database.query("main",null,null,null,null,null,null);

        if (cursor.moveToFirst()&&cursor.getString(cursor.getColumnIndex("_city"))!=null ){
        cursor.moveToFirst();
        String data=cursor.getString(cursor.getColumnIndex("_city"));
        double latitude= Double.parseDouble(cursor.getString(cursor.getColumnIndex("_latitude")));
        double longitude=Double.parseDouble(cursor.getString(cursor.getColumnIndex("_Longitude")));


        Cordinates coordinates=new Cordinates(latitude,longitude,data);
            cursor.close();
            database.close();
            dbHelper.close();
        return coordinates;
        }
        else  cursor.close();
        database.close();
        dbHelper.close();
        throw new InterruptedException();
    }


    boolean setCoordinates(double latitude,double longitude,String city){
          DBHelper dbHelper;
        ContentValues contentValues;
        SQLiteDatabase database;
        Cursor cursor;

        database=SQLiteDatabase.openOrCreateDatabase(dbPath,null);
        int version=database.getVersion();
        database.close();

        dbHelper=new DBHelper(context,nameDB,version,null);

        try {
            database=dbHelper.getWritableDatabase();
        }catch (SQLException e){database=dbHelper.getReadableDatabase();}

        cursor=database.query("main",null,null,null,null,null,null);

        if (checkDBCoordinates()){
            contentValues=new ContentValues();
            Double lat=latitude;
            Double lon=longitude;
            cursor.moveToFirst();
            contentValues.put("_latitude",lat.toString());
            contentValues.put("_Longitude",lon.toString());
            contentValues.put("_city",city);

            database.update("main",contentValues,"_city = ?",new String[]{cursor.getString(cursor.getColumnIndex("_city"))});

            cursor.close();
            database.close();
            dbHelper.close();
        }

        else {
        contentValues=new ContentValues();
        Double lat=latitude;
        Double lon=longitude;
        contentValues.put("_latitude",lat.toString());
        contentValues.put("_Longitude",lon.toString());
        contentValues.put("_city",city);
        database.insert("main",null,contentValues);
        cursor.close();
        database.close();
        dbHelper.close();
        }
        return false;
    }




    boolean checkDBCoordinates(){

        DBHelper dbHelper;
        SQLiteDatabase database;
        Cursor cursor;

        database=SQLiteDatabase.openOrCreateDatabase(dbPath,null);
        int version=database.getVersion();
        database.close();

     dbHelper=new DBHelper(context,nameDB,version,null);

        try {
            database=dbHelper.getWritableDatabase();
        }catch (SQLException e){database=dbHelper.getReadableDatabase();}

        cursor=database.query("main",null,null,null,null,null,null);

 if (cursor.moveToFirst()){
        if (cursor.getString(cursor.getColumnIndex("_city"))!=null){
            cursor.close();database.close();dbHelper.close();
            return true;
        }
 }
        cursor.close();database.close();dbHelper.close();
        return false;
    }

    String[] getDBWeather(){

        //TODO
        return new String[5];
    }



   void setDBWeather(String Data,String Request){
        //TODO

   }



  private boolean checkDB(){
       SQLiteDatabase check=null;

       try {
           check=SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READONLY);
       }catch (SQLException e){}

       if (check!=null){check.close();}
        return check!=null;
   }

   void createDB(){
        String SQLRequest;
       DBHelper dbHelper;
       SQLiteDatabase database;
        if (checkDB()){}

        else {
             SQLRequest = "create table main (_id integer primary key autoincrement, _latitude text, _Longitude text, _city text);";
            dbHelper=new DBHelper(context,nameDB,1,SQLRequest);

            try {
                 database=dbHelper.getWritableDatabase();
            }catch (SQLException e){database=dbHelper.getReadableDatabase();}
dbHelper.close(); database.close();

             SQLRequest = "create table weather (_id integer primary key autoincrement, _time text, _request text, _city text);";
             dbHelper=new DBHelper(context,nameDB,2,SQLRequest);
                         try {
                database=dbHelper.getWritableDatabase();
            }catch (SQLException e){database=dbHelper.getReadableDatabase();}
            dbHelper.close(); database.close();

        }

   }
}
