package com.example.wanghy.restauranthygienechecker.common;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by coollive on 18/3/4.
 */

public class StringRequestWithFood extends StringRequest {
    public StringRequestWithFood(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public StringRequestWithFood(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("x-api-version", "2");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("accept", "application/json");
        headers.put("content-type", "application/json");
        headers.put("Host", "api.ratings.food.gov.uk");
        headers.put("Accept-Encoding", "gzip, deflate");
        Log.e("SimpleSearchFragment","headers: "+headers.toString()+"\n");
        return headers;
    }
}
