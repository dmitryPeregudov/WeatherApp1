package com.dethdemonaexemple.weatherapp;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import android.widget.Toast;

import com.dethdemonaexemple.weatherapp.DBCLASS.DBPresenter;
import com.dethdemonaexemple.weatherapp.RESTAPICLASS.Cordinates;
import com.dethdemonaexemple.weatherapp.RESTAPICLASS.ListData;
import com.dethdemonaexemple.weatherapp.RESTAPICLASS.NetworkService;
import com.dethdemonaexemple.weatherapp.RESTAPICLASS.Respone;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter {

private DBPresenter dbPresenter;
Context context;



    public Presenter(Context context) {
        this.context = context;
        dbPresenter=new DBPresenter(context);

    }



    Cordinates getCoordinates() throws InterruptedException {



    Cordinates cordinates=dbPresenter.getCoordinates();

    return cordinates;
}

    void createDB(){
        dbPresenter.createDB();
    }


    boolean checkCoordinates(){
        return dbPresenter.checkDBCoordinates();
    }


    private void createAlertDialog(final Context context, double lat, double lon, String city){
        final double lo=lon;
        final double la=lat;
        final String adress=city;
        DialogInterface.OnClickListener onClickListener=new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

                    case AlertDialog.BUTTON_POSITIVE:
                        dbPresenter.setCoordinates(la, lo, adress);startActiv(context);
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

    private void startActiv(Context context){
        Intent intent= new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

   void setCoordinates(Context context,double latitude, double longitude){


    Geocoder geocoder=new Geocoder(context, Locale.getDefault());

       try {
           List<Address> a =
                   geocoder.getFromLocation(latitude, longitude, 1);

           if (a.size() >0) {
               String address = a.get(0).getLocality();




               if (address==null) {
                   Toast.makeText(context, "No city matching. Try again", Toast.LENGTH_LONG).show();


               } else { address+=","+a.get(0).getCountryCode();
                   createAlertDialog(context,latitude,longitude,address);




               }

           } else Toast.makeText(context, "No city matching. Try again", Toast.LENGTH_LONG).show();

       }
       catch (IOException e) {
           e.printStackTrace();
       }
   }

  List<FieldForAdapter> getDBWeather()throws NullPointerException{
        List<String> prcable=dbPresenter.getDBWeather();
        List<FieldForAdapter> fields=new ArrayList<>();
        if (prcable==null){throw new NullPointerException();}
        else {
            for (int i=0;i<prcable.size();i++){
            fields.add(parseData(prcable.get(i)));
            }
        }
return fields;
  }

  void getRestWeather(){
      final String city;
      try {
          Cordinates cord= getCoordinates();
           city=cord.city;
      } catch (InterruptedException e) {
         e.printStackTrace();return;
      }


if (checkConnection()){

      NetworkService.getInstance()
              .getJSONApi()
              .getPostWithID(city,context.getResources().getString(R.string.weather_api_key))
              .enqueue(new Callback<JsonObject>() {
                  @Override
                  public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                      if (response.isSuccessful()&&response.body()!=null) {

                          setDbWeather(response.body().toString(),city,getTime());

                          Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
                          intent.putExtra("response","successful");
                          context.sendBroadcast(intent);
                      } else {
                          if (response.body()==null){Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();}
                          else {
                          switch (response.code()) {
                              case 404:
                                  Toast.makeText(context,"Error 404",Toast.LENGTH_LONG).show();
                                  break;
                              case 500:
                                  Toast.makeText(context,"Error 500",Toast.LENGTH_LONG).show();
                                  break;
                          }
                          }

                      }

                  }

                  @Override
                  public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                      Toast.makeText(context,"Response failure",Toast.LENGTH_LONG).show();

                      t.printStackTrace();
                  }
              });
  } else Toast.makeText(context,"Connection error",Toast.LENGTH_LONG).show();
    }

 private boolean checkConnection(){
      ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
      if (manager!=null){
          NetworkInfo info=manager.getActiveNetworkInfo();
          if (info!=null){
              if (info.getState()== NetworkInfo.State.CONNECTED){
                  return true;
              }
          }
      }
else {
        Toast.makeText(context,"Missing internet connection",Toast.LENGTH_LONG).show();}
       return false;
  }


 private FieldForAdapter parseData(String response){
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      ListData data=gson.fromJson(response,ListData .class);
      List<Respone> param=data.getData();
      FieldForAdapter forAdapter=new FieldForAdapter(param.get(0));
      return forAdapter;

  }

 private void setDbWeather(String Data,String city,String time){
     dbPresenter.setDBWeather(Data,city,time);
 }

 private String getTime(){
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
    SimpleDateFormat mdformat1 = new SimpleDateFormat("HH:mm:ss");
    String strDate = "" + mdformat.format(calendar.getTime())+" "+mdformat1.format(calendar.getTime());
    return strDate;
}

}
