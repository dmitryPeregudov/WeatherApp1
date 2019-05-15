package com.dethdemonaexemple.weatherapp.RESTAPICLASS;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {

    @GET("current?")
    public Call<JsonObject> getPostWithID(@Query("city") String city, @Query("key") String key);
}
