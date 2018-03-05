package com.example.wanghy.restauranthygienechecker.entity;

/**
 * Created by coollive on 18/3/3.
 */

public class Business {
    private int bid;
    private String businessName;
    private String addressLine;
    private String phone;
    private String distance;
    private String ratingValue;
    private String longitude;
    private String latitude;

    public Business(int bid, String businessName, String addressLine, String line, String phone, String distance, String ratingValue) {
        this.bid = bid;
        this.businessName = businessName;
        this.addressLine  = addressLine;
        this.phone        = phone;
        this.distance     = distance;
        this.ratingValue  = ratingValue;
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
}
