package com.dethdemonaexemple.weatherapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
private List<FieldForAdapter> info=new ArrayList<>();



public void setItems(List<FieldForAdapter>data){
    for (int i=0;i<data.size();i++) {
        info.add(data.get(i));
    }
}



    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_view, viewGroup, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int a) {

        weatherViewHolder.bind(info.get(a));

    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder{
     private TextView cityName;
     private TextView obTime;
     private TextView sunrise;
     private TextView sunset;
     private TextView windSpeed;
     private TextView windDirection;
     private TextView temperature;
     private TextView rh;




        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            cityName=itemView.findViewById(R.id.cityName);
            obTime=itemView.findViewById(R.id.obTime);
            sunrise=itemView.findViewById(R.id.sunrise);
            sunset=itemView.findViewById(R.id.sunset);
            windSpeed=itemView.findViewById(R.id.windSpeed);
            windDirection=itemView.findViewById(R.id.windDirection);
            temperature=itemView.findViewById(R.id.temperature);
            rh=itemView.findViewById(R.id.rh);
        }

        public void bind(FieldForAdapter field){
            cityName.setText("City name : "+field.cityName);
            obTime.setText("Last observation time : "+field.obTime);
            sunrise.setText("Sunrise : "+field.sunrise);
            sunset.setText("Sunset : "+field.sunset);
            windSpeed.setText("Wind speed : "+field.windSpeed);
            windDirection.setText("Wind direction : "+field.windDirection);
            temperature.setText("Temperature : "+field.temperature+" C");
            rh.setText("Relative humidity : "+field.rh+" %");
        }
    }
}
