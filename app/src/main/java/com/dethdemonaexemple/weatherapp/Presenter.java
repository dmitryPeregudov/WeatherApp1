package com.dethdemonaexemple.weatherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Presenter {

private  static String TAG="MyLog";
private DBPresenter dbPresenter;
Context context;



    public Presenter(Context context) {
        this.context = context;
        dbPresenter=new DBPresenter(context);

    }



    Cordinates getСoordinates() throws InterruptedException {



    Cordinates cordinates=dbPresenter.getCoordinates();

    return cordinates;
}

    void createDB(){
        dbPresenter.createDB();
    }


    boolean checkCoordinates(){
        return dbPresenter.checkDBCoordinates();
    }


    private void createAlertDialog(Context context,  double lat, double lon,  String city){
        final double lo=lon;
        final double la=lat;
        final String adress=city;
        DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

                    case AlertDialog.BUTTON_POSITIVE:
                        dbPresenter.setCoordinates(la, lo, adress);
                        break;
                    case AlertDialog.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setTitle("Set new Location")
                .setMessage("This action changed location.\n"+"new latitude = "+lat+"\nNew longitude ="+lon+"\nNew city = "+city+"\nAre you sure?")
                .setPositiveButton("Yes",onClickListener)
                .setNegativeButton("No",onClickListener).create().show();
    }

   void setCoordinates(Context context,double latitude, double longitude){


    Geocoder geocoder=new Geocoder(context, Locale.getDefault());
       try {
           List<Address> a =
                   geocoder.getFromLocation(latitude, longitude, 1);
           if (a.size() >0) {
               String address = a.get(0).getLocality();



               if (address == null) {
                   Toast.makeText(context, "No city matching. Try again", Toast.LENGTH_LONG).show();

               } else {createAlertDialog(context,latitude,longitude,address);




               }

           } else Toast.makeText(context, "No city matching. Try again", Toast.LENGTH_LONG).show();

       }
       catch (IOException e) {
           e.printStackTrace();
       }
   }

  void getDBWeather(){}

  void getRestWeather(){}

  boolean checkConnection(){

       return false;
  }


  void parseData(){  }




}
