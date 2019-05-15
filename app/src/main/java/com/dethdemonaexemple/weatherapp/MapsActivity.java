package com.dethdemonaexemple.weatherapp;




import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import com.dethdemonaexemple.weatherapp.RESTAPICLASS.Cordinates;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
       try {
           mapFragment.getMapAsync(this);
       }catch (NullPointerException e){}

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.clear();
        Presenter a=new Presenter(this);
         try{
             Cordinates cordinates= a.getСoordinates();
        LatLng latLng=new LatLng(cordinates.latitude,cordinates.longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("YOUR CITY"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
         }
         catch (InterruptedException e){}


    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("YOUR CITY"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        Presenter a=new Presenter(this);
        a.setCoordinates(this,latLng.latitude,latLng.longitude);

    }
}