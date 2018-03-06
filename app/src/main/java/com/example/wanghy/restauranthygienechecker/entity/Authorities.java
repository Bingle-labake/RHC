package com.example.wanghy.restauranthygienechecker.entity;

import com.example.wanghy.restauranthygienechecker.common.Consts;

import java.io.Serializable;

/**
 * Created by coollive on 18/3/3.
 */

public class Authorities implements Serializable{
    private String id = "";
    private int pageNumber = 1;
    private int pageSize   = 15;

    public Authorities() {
    }

    public String getUrl() {
        String url = "";
        url = Consts.FOOD_HOST+"Authorities";
        return url;
    }

    public String getUrl(int pageNumber, int pageSize) {
        String url = "";
        url = Consts.FOOD_HOST+"Authorities/"+pageNumber+"/"+pageSize;
        return url;
    }

    public String getUrl(int id) {
        String url = "";
        url = Consts.FOOD_HOST+"Authorities/"+id;
        return url;
    }

    public String getBaseUrl() {
        String url = "";
        url = Consts.FOOD_HOST+"Authorities/basic";
        return url;
    }

    public String getBaseUrl(int pageNumber, int pageSize) {
        String url = "";
        url = Consts.FOOD_HOST+"Authorities/basic/"+pageNumber+"/"+pageSize;
        return url;
    }
}
