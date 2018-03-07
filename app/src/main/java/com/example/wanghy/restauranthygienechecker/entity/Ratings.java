package com.example.wanghy.restauranthygienechecker.entity;

import com.example.wanghy.restauranthygienechecker.common.Consts;

import java.io.Serializable;

/**
 * Created by wanghy on 18/3/3.
 */

public class Ratings implements Serializable{
    public Ratings() {
    }

    public String getUrl() {
        String url = "";
        url = Consts.FOOD_HOST+"Ratings";
        return url;
    }
}
