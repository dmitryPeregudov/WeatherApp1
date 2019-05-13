package com.dethdemonaexemple.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
Presenter presenter;

static  String TAG="MyLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter=new Presenter(this);
        presenter.createDB();


        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID()
                .enqueue(new Callback<ParsedRespone>() {
                    @Override
                    public void onResponse(@NonNull Call<ParsedRespone> call, @NonNull Response<ParsedRespone> response) {
                        ParsedRespone respone = response.body();
                        Log.d(TAG,""+response.raw());

                    }

                    @Override
                    public void onFailure(@NonNull Call<ParsedRespone> call, @NonNull Throwable t) {

                        Log.d(TAG,"FUCK");
                        t.printStackTrace();
                    }
                });

        if (presenter.checkCoordinates()){


        }
        else {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);finish();
        }

    }


}
