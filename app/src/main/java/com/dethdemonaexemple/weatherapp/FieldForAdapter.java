package com.dethdemonaexemple.weatherapp;

import com.dethdemonaexemple.weatherapp.RESTAPICLASS.Respone;

public class FieldForAdapter {

    String sunrise;
    String sunset;
    String obTime;
    String cityName;
    String windDirection;
    double windSpeed;
    double temperature;
    int rh;
    Respone respone;

    public FieldForAdapter(Respone respone) {
        this.respone = respone;
        sunrise=respone.getSunrise();
        sunset=respone.getSunset();
        obTime=respone.getObTime();
        cityName=respone.getCityName();
        windDirection=respone.getWindCdirFull();
        windSpeed=respone.getWindSpd();
        temperature=respone.getTemp();
        rh=respone.getRh();
    }
}
