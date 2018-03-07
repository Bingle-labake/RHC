package com.example.wanghy.restauranthygienechecker.entity;

import com.example.wanghy.restauranthygienechecker.common.Consts;

import java.io.Serializable;

/**
 * Created by wanghy on 18/3/3.
 */

public class Regions implements Serializable{
    private String id = "";
    private int pageNumber = 1;
    private int pageSize   = 15;

    public Regions() {
    }

    public String getUrl() {
        String url = "";
        url = Consts.FOOD_HOST+"Regions";
        return url;
    }

    public String getUrl(int pageNumber, int pageSize) {
        String url = "";
        url = Consts.FOOD_HOST+"Regions/"+pageNumber+"/"+pageSize;
        return url;
    }

    public String getUrl(int id) {
        String url = "";
        url = Consts.FOOD_HOST+"Regions/"+id;
        return url;
    }

    public String getBaseUrl() {
        String url = "";
        url = Consts.FOOD_HOST+"Regions/basic";
        return url;
    }

    public String getBaseUrl(int pageNumber, int pageSize) {
        String url = "";
        url = Consts.FOOD_HOST+"Regions/basic/"+pageNumber+"/"+pageSize;
        return url;
    }
}
