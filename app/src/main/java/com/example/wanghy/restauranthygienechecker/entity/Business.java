package com.example.wanghy.restauranthygienechecker.entity;

import java.io.Serializable;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
/**
 * Created by wanghy on 18/3/3.
 */

@Entity(tableName = "Business")
public class Business implements Serializable {
    @PrimaryKey
    @NonNull private int bid;
    @ColumnInfo(name = "businessName")
    private String businessName;
    private String addressLine;
    private String phone;
    private String distance;

    @ColumnInfo(name = "ratingValue")
    private String ratingValue;
    private String longitude;
    private String latitude;
//    private String email;
//    private String web;

    public Business(int bid, String businessName, String ratingValue){
        this.bid = bid;
        this.businessName = businessName;
        this.ratingValue = ratingValue;
        this.longitude= "";
        this.latitude= "";
        this.businessName= "";
        this.addressLine= "";
        this.phone= "";
        this.distance= "";
    }

    public Business(int bid, String businessName, String addressLine, String line, String phone, String distance, String ratingValue) {
        this.bid = bid;
        this.businessName = businessName;
        this.addressLine  = addressLine;
        this.phone        = phone;
        this.distance     = distance;
        this.ratingValue  = ratingValue;
//        this.email        = email;
//        this.web          = web;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

//    public String getEmail() {
//        return email;
//    }

//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getweb() {
//        return web;
//    }
//
//    public void setweb(String web) {
//        this.web = web;
//    }


}
