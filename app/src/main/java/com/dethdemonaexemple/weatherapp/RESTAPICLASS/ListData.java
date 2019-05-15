package com.dethdemonaexemple.weatherapp.RESTAPICLASS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

 public class ListData {
    @SerializedName("data")
    @Expose
    private List<Respone> data = null;
    @SerializedName("count")
    @Expose
    private Integer count;

    public List<Respone> getData() {
        return data;
    }

    public void setData(List<Respone> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
