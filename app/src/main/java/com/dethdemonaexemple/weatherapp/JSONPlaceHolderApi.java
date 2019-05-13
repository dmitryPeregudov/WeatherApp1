package com.dethdemonaexemple.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
String api="ed3646a8106140bf8a0be65ba7fdeb7a";
    @GET("current?city=Харьков,ua&key="+api)
    public Call<ParsedRespone> getPostWithID();
}
