package com.example.wanghy.restauranthygienechecker.entity;

import com.example.wanghy.restauranthygienechecker.common.Consts;

/**
 * Created by coollive on 18/3/3.
 */

public class Establishment {
    private String name = "";
    private String address = "";
    private String businessTypeId = "";
    private String schemeTypeKey = "";
    private String ratingKey = "";
    private String ratingOperatorKey = "";
    private String localAuthorityId = "";
    private String countryId = "";
    private String sortOptionKey = "";
    private double longitude = 0.0;
    private double latitude  = 0.0;
    private int pageNumber = 1;
    private int pageSize   = 10;
    private int maxDistanceLimit = 15;

    public Establishment() {
    }

    public Establishment(String name, String address) {
        this.name = name;
        this.address  = address;
    }

    public Establishment(double longitude, double latitude, int maxDistanceLimit) {
        this.longitude        = longitude;
        this.latitude         = latitude;
        this.maxDistanceLimit = maxDistanceLimit;
    }

    public String getUrl() {
        String url = "";
        if(this.name == "" && this.address == "" && this.longitude == 0.0 && this.latitude == 0.0) {
            url = Consts.FOOD_HOST+"Establishments/basic/%d/%d";
            url = String.format(url, this.pageNumber, this.pageSize);
        }else {
            url = Consts.FOOD_HOST+"Establishments?";
            if(this.name != "") {
                url += "&name="+this.name;
            }
            if(this.address != "") {
                url += "&address="+this.address;
            }
            if(this.longitude != 0.0) {
                url += "&longitude="+String.valueOf(this.longitude);
            }
            if(this.latitude != 0.0) {
                url += "&latitude="+String.valueOf(this.latitude);
            }
            if(this.maxDistanceLimit > 0) {
                url += "&maxDistanceLimit="+String.valueOf(this.maxDistanceLimit);
            }
            if(this.businessTypeId != "") {
                url += "&businessTypeId="+this.businessTypeId;
            }
            if(this.schemeTypeKey != "") {
                url += "&schemeTypeKey="+this.schemeTypeKey;
            }
            if(this.ratingKey != "") {
                url += "&ratingKey="+this.ratingKey;
            }
            if(this.ratingOperatorKey != "") {
                url += "&ratingOperatorKey="+this.ratingOperatorKey;
            }
            if(this.localAuthorityId != "") {
                url += "&localAuthorityId="+this.localAuthorityId;
            }
            if(this.countryId != "") {
                url += "&countryId="+this.countryId;
            }
            if(this.sortOptionKey != "") {
                url += "&sortOptionKey="+this.sortOptionKey;
            }
            if(this.pageNumber > 0) {
                url += "&pageNumber="+String.valueOf(this.pageNumber);
            }
            if(this.pageSize > 0) {
                url += "&pageSize="+String.valueOf(this.pageSize);
            }
        }
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getSchemeTypeKey() {
        return schemeTypeKey;
    }

    public void setSchemeTypeKey(String schemeTypeKey) {
        this.schemeTypeKey = schemeTypeKey;
    }

    public String getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(String ratingKey) {
        this.ratingKey = ratingKey;
    }

    public String getRatingOperatorKey() {
        return ratingOperatorKey;
    }

    public void setRatingOperatorKey(String ratingOperatorKey) {
        this.ratingOperatorKey = ratingOperatorKey;
    }

    public String getLocalAuthorityId() {
        return localAuthorityId;
    }

    public void setLocalAuthorityId(String localAuthorityId) {
        this.localAuthorityId = localAuthorityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getSortOptionKey() {
        return sortOptionKey;
    }

    public void setSortOptionKey(String sortOptionKey) {
        this.sortOptionKey = sortOptionKey;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxDistanceLimit() {
        return maxDistanceLimit;
    }

    public void setMaxDistanceLimit(int maxDistanceLimit) {
        this.maxDistanceLimit = maxDistanceLimit;
    }

}
