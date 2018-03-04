package com.example.wanghy.restauranthygienechecker;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wanghy.restauranthygienechecker.adapter.BusinessListAdapter;
import com.example.wanghy.restauranthygienechecker.common.Consts;
import com.example.wanghy.restauranthygienechecker.entity.BitmapCache;
import com.example.wanghy.restauranthygienechecker.entity.Business;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wanghy on 2018/2/26.
 */

public class SimpleSearchFragment extends Fragment {
    View myView;
    private ListView lv;
    private List<Business> list;

    RequestQueue requestQueue;

    private Button volley_get;
    private Button volley_post;
    private Button volley_json;
    private Button volley_imageRequest;
    private Button volley_imageLader;
    private Button netWorkImageView;
    private ImageView volley_image;
    private SimpleDraweeView volley_imageNet;
    private TextView volley_result;
    private EditText simple_search_input;

    private final String TAG = "SimpleSearchFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.simple_search,null);
        initViews(myView);
        return myView;
    }

    private void initViews(View view) {
        lv = (ListView) view.findViewById(R.id.listViewF);
        simple_search_input = (EditText)view.findViewById(R.id.simple_search_input);
        simple_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i("---","搜索操作执行");
                    String wd = simple_search_input.getText().toString();
                    get(wd, "");
                }
                return false;
            }
        });
        reLoadData();
    }


    private void reLoadData() {
        String address = "Aster House";//定位获取地址
        get("", address);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    BusinessListAdapter adapter = new BusinessListAdapter(list);
                    lv.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(String response) {
        list = new ArrayList<Business>();
        try {
            JSONObject obj            = new JSONObject(response);
            JSONArray establishments = obj.getJSONArray("establishments");
            Business business = null;
            for (int i = 0; i < establishments.length(); i++) {
                JSONObject data = establishments.getJSONObject(i);

                int bid                = data.getInt("FHRSID");
                String businessName    = data.getString("BusinessName");
                String addressLine     = data.getString("AddressLine1");
                String phone           = data.getString("Phone");
                String distance        = data.getString("Distance");
                String ratingValue     = data.getString("RatingValue");

                business = new Business(bid, businessName, addressLine, addressLine, phone, distance, ratingValue);
                list.add(business);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mHandler.obtainMessage(0).sendToTarget();
    }


    /**
     * get
     */
    public void get(String name, String address){
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());
        //创建一个请求
        //String url = Consts.FOOD_HOST+"Establishments?name=%s&address=%s&longitude=%s&latitude=%s&maxDistanceLimit=%s&businessTypeId=%s&schemeTypeKey=%s&ratingKey=%s&ratingOperatorKey=%s&localAuthorityId=%s&countryId=%s&sortOptionKey=%s&pageNumber=%s&pageSize=%s";
        String url = Consts.FOOD_HOST+"Establishments?name=%s&address=%s";
        url = String.format(url, name, address);
        Log.e(TAG,"url: "+url+"\n");
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            //正确接收数据回调
            @Override
            public void onResponse(String s) {
                Log.e(TAG,"ret="+s+"\n");
                loadData(s);
            }
        }, new Response.ErrorListener() {//异常后的监听数据
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volley_result.setText("加载错误"+volleyError);
            }
        });
        //将get请求添加到队列中
        requestQueue.add(stringRequest);
    }

    /**
     * post
     */
    private void post(){
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());
        //创建一个请求
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                volley_result.setText(s);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volley_result.setText("加载错误"+volleyError);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> map = new HashMap<>();
                // map.put("value1","param1");//传入参数

                return map;
            }
        };

        //将post请求添加到队列中
        requestQueue.add(stringRequest);
    }

    /**
     * json
     */
    private void json(){
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个请求
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                //TestData data = new Gson().fromJson(String.valueOf(jsonObject),TestData.class);

                volley_result.setText(jsonObject.toString());


                Log.e(TAG,"data="+jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volley_result.setText("加载错误"+volleyError);

            }
        });

        //将创建的请求添加到队列中
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * 加载图片
     */
    private void image(){
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个请求
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        //第二个参数,第三个：宽高，第四个：图片质量
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //正确接收图片
                volley_image.setImageBitmap(bitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volley_image.setImageResource(R.mipmap.ic_launcher);
            }
        });

        //将创建的请求添加到队列中
        requestQueue.add(imageRequest);
    }

    /**
     * imageLoader
     */
    private void imageLoader(){
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个请求

        ImageLoader imageLoader = new ImageLoader(requestQueue,new BitmapCache());//带缓存

        //加载图片
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        //加载不到，加载失败
        ImageLoader.ImageListener imageLister = imageLoader.getImageListener(volley_imageNet,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        imageLoader.get(url,imageLister);
    }

    /**
     * netWorkImageView
     */
    private void netWorkImageView(){
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个imageLoader
        ImageLoader imageLoader = new ImageLoader(requestQueue,new BitmapCache());

        //默认图片设置
        volley_imageNet.setImageResource(R.mipmap.ic_launcher);

        //加载图片
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        volley_imageNet.setImageURI(url,imageLoader);
    }
}
