package com.dethdemonaexemple.weatherapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static final String BROADCAST_ACTION ="com.dethdemonaexemple.weatherapp" ;
    Presenter presenter;
    RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    int PERMISSION_REQUEST_CODE;
    BroadcastReceiver broadcastReceiver;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.load) {
            clicker();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);
        presenter.createDB();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        permissionCheck();
        checker();
        setBroadcast();


    }
 void setBroadcast(){
     broadcastReceiver=new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             String a=intent.getStringExtra("response");
             if (a.equals("successful")){
                 updateData();
             }
         }
     };
     IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
     registerReceiver(broadcastReceiver, intFilt);
 }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    public void clicker() {
        presenter.getRestWeather();

        try {
            updateData();
        } catch (NullPointerException e) {
            presenter.getRestWeather();
            dialogSet();
        }

    }

    void permissionCheck() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        }
    }

    void checker() {
        if (presenter.checkCoordinates()) {

            try {
                updateData();


            } catch (NullPointerException e) {
                presenter.getRestWeather();
                dialogSet();
            }
        } else {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void updateData() {
        weatherAdapter = new WeatherAdapter();
        recyclerView.setAdapter(weatherAdapter);
        weatherAdapter.setItems(presenter.getDBWeather());


    }

    void dialogSet() {

        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case AlertDialog.BUTTON_POSITIVE:
                        finish();
                        break;

                }
            }
        };

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Weather data in local database is missing")
                .setMessage("Get data from internet and close app?")
                .setPositiveButton("Yes", onClickListener)
                .create().show();
    }

}
