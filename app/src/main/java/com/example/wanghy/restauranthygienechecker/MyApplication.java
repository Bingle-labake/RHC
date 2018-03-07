package com.example.wanghy.restauranthygienechecker;
import android.app.Application;

import com.example.wanghy.restauranthygienechecker.entity.Business;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;


public class MyApplication extends Application {
    private double longitude = 0.0;
    private double latitude = 0.0;
    List<Business> searchlist = null;

    private static MyApplication instance = null;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //Fresco初始化
        Fresco.initialize(getApplicationContext());

    }

    public List<Business> getSearchlist() {
        return searchlist;
    }

    public void setSearchlist(List<Business> searchlist) {
        this.searchlist = searchlist;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }



}
