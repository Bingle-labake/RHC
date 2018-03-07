package com.example.wanghy.restauranthygienechecker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.example.wanghy.restauranthygienechecker.common.StringRequestWithFood;
import com.example.wanghy.restauranthygienechecker.entity.BitmapCache;
import com.example.wanghy.restauranthygienechecker.entity.Business;
import com.example.wanghy.restauranthygienechecker.entity.Establishment;
import com.example.wanghy.restauranthygienechecker.entity.SearchList;
import com.facebook.drawee.view.SimpleDraweeView;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.location.LocationManager.NETWORK_PROVIDER;


/**
 * Created by wanghy on 2018/2/26.
 */

public class SimpleSearchFragment extends Fragment {
    View myView;
    private ListView lv;
    RequestQueue requestQueue;

    private Button volley_get;
    private Button volley_post;
    private Button volley_json;
    private Button volley_imageRequest;
    private Button volley_imageLader;
    private Button netWorkImageView;
    private Button simple_search_btn;
    private ImageView volley_image;
    private SimpleDraweeView volley_imageNet;
    private TextView volley_result;
    private EditText simple_search_input;
    private double longitude = -1.9;
    private double latitude = 52.5;
    BusinessListAdapter adapter;
    private final String TAG = "SimpleSearchFragment";
    List<Business> searchlist = null;

    public BusinessListAdapter getAdapter(){
        return adapter;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.simple_search, null);
        initViews(myView);
        return myView;
    }

    private void initViews(View view) {
        lv = (ListView) view.findViewById(R.id.listViewF);
        simple_search_input = (EditText) view.findViewById(R.id.simple_search_input);
        simple_search_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == KeyEvent.KEYCODE_ENTER) {
                    ((InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SimpleSearchFragment.this.getActivity().getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String wd = simple_search_input.getText().toString();
                    Establishment establishment = new Establishment(wd, wd);
                    get(establishment);
                }
                return false;
            }
        });
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("onclick", "clicked"+i);
//                Intent ii = new Intent(getActivity(), DetailActivity.class);
//                ii.putExtra("establishment",adapter.getEst(i) );
//                startActivity(ii);
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchlist != null) {
                    Intent ii = new Intent(SimpleSearchFragment.this.getContext(), MapsActivity.class);
                    SimpleSearchFragment.this.getContext().startActivity(ii);
                }

            }
        });


        simple_search_btn = (Button) view.findViewById(R.id.simple_search_btn);
        simple_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wd = simple_search_input.getText().toString();
                Establishment establishment = new Establishment(wd, wd);
                get(establishment);
            }
        });
        reLoadData();
    }



    private void reLoadData() {
        String address = "Aster House";//定位获取地址
        Establishment establishment = new Establishment(longitude, latitude, 15);
        get(establishment);

        String lngandlat = getLngAndLat(SimpleSearchFragment.this.getContext());
//        Log.e(TAG, "longitude and latitude: " + lngandlat + "\n");
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    List<Business> list = (List<Business>) msg.obj;
                    searchlist = list;
                    MyApplication.getInstance().setSearchlist(list);
                    adapter = new BusinessListAdapter(list);
                    lv.setAdapter(adapter);

                    break;
                default:
                    break;
            }
        }
    };

    private void loadData(String response) {
        try {
            List<Business> list = new ArrayList<Business>();
            JSONObject obj = new JSONObject(response);
            JSONArray establishments = obj.getJSONArray("establishments");
            for (int i = 0; i < establishments.length(); i++) {
                JSONObject data = establishments.getJSONObject(i);

                int bid = data.getInt("FHRSID");
                String businessName = data.getString("BusinessName");
                String addressLine = data.getString("AddressLine1");
                String phone = data.getString("Phone");
                String distance = data.getString("Distance");
                String ratingValue = data.getString("RatingValue");

                JSONObject geocode = data.getJSONObject("geocode");
                String longitude = geocode.getString("longitude");
                String latitude = geocode.getString("latitude");
                if (distance == "") {
                    distance = "--";
                }
                Business business = new Business(bid, businessName, addressLine, addressLine, phone, distance, ratingValue);
                business.setLongitude(longitude);
                business.setLatitude(latitude);
                list.add(business);
            }

            if (!list.isEmpty()) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = list;
                mHandler.sendMessage(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * get
     */
    public void get(Establishment establishment) {
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());
        //String url = Consts.FOOD_HOST+"Establishments?name=%s&address=%s&longitude=%s&latitude=%s&maxDistanceLimit=%s&businessTypeId=%s&schemeTypeKey=%s&ratingKey=%s&ratingOperatorKey=%s&localAuthorityId=%s&countryId=%s&sortOptionKey=%s&pageNumber=%s&pageSize=%s";
        String url = establishment.getUrl();
        Log.e(TAG, "url: " + url + "\n");

        int DEFAULT_TIMEOUT_MS = 10000;
        int DEFAULT_MAX_RETRIES = 3;
        StringRequestWithFood stringRequest = new StringRequestWithFood(url, new Response.Listener<String>() {
            //正确接收数据回调
            @Override
            public void onResponse(String s) {
                Log.e(TAG, "ret=" + s + "\n");
                loadData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(SimpleSearchFragment.this.getActivity(), "加载错误" + volleyError, Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DEFAULT_TIMEOUT_MS, DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //将get请求添加到队列中
        requestQueue.add(stringRequest);
    }

    /**
     * post
     */
    private void post() {
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
                volley_result.setText("加载错误" + volleyError);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
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
    private void json() {
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个请求
        String url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                //TestData data = new Gson().fromJson(String.valueOf(jsonObject),TestData.class);

                volley_result.setText(jsonObject.toString());


                Log.e(TAG, "data=" + jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volley_result.setText("加载错误" + volleyError);

            }
        });

        //将创建的请求添加到队列中
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * 加载图片
     */
    private void image() {
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
    private void imageLoader() {
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个请求

        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());//带缓存

        //加载图片
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        //加载不到，加载失败
        ImageLoader.ImageListener imageLister = imageLoader.getImageListener(volley_imageNet, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(url, imageLister);
    }

    /**
     * netWorkImageView
     */
    private void netWorkImageView() {
        //创建一个请求队列
        requestQueue = Volley.newRequestQueue(SimpleSearchFragment.this.getContext());

        //创建一个imageLoader
        ImageLoader imageLoader = new ImageLoader(requestQueue, new BitmapCache());

        //默认图片设置
        volley_imageNet.setImageResource(R.mipmap.ic_launcher);

        //加载图片
        String url = "http://img5.mtime.cn/mg/2016/12/26/164311.99230575.jpg";
        volley_imageNet.setImageURI(url, imageLoader);
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    private String getLngAndLat(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            if (ActivityCompat.checkSelfPermission(SimpleSearchFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SimpleSearchFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(SimpleSearchFragment.this.getActivity(), "getLngAndLat 加载错误", Toast.LENGTH_LONG).show();
                return "0.0 , 0.0";
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork();
            }
        } else {    //从网络获取经纬度
            locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 0, (android.location.LocationListener) locationListener);
            Location location = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        MyApplication.getInstance().setLatitude(latitude);
        MyApplication.getInstance().setLatitude(longitude);
        return longitude + "," + latitude;
    }


    //从网络获取经纬度
    private String getLngAndLatWithNetwork() {
        LocationManager locationManager = (LocationManager) SimpleSearchFragment.this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(SimpleSearchFragment.this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SimpleSearchFragment.this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(SimpleSearchFragment.this.getActivity(), "getLngAndLatWithNetwork 加载错误", Toast.LENGTH_LONG).show();
            return "0.0 , 0.0";
        }
        locationManager.requestLocationUpdates(NETWORK_PROVIDER, 1000, 0, (android.location.LocationListener) locationListener);
        Location location = locationManager.getLastKnownLocation(NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        MyApplication.getInstance().setLatitude(latitude);
        MyApplication.getInstance().setLatitude(longitude);
        return longitude + "," + latitude;
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//            Toast.makeText(SimpleSearchFragment.this.getActivity(), "onLocationChanged函数被触发！", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "时间：" + location.getTime());
            Log.i(TAG, "经度：" + location.getLongitude());
            Log.i(TAG, "纬度：" + location.getLatitude());
            Log.i(TAG, "海拔：" + location.getAltitude());

            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                //GPS状态为可见时
                case LocationProvider.AVAILABLE:
                    Toast.makeText(SimpleSearchFragment.this.getActivity(), "onStatusChanged：当前GPS状态为可见状态", Toast.LENGTH_SHORT).show();
                    break;
                //GPS状态为服务区外时
                case LocationProvider.OUT_OF_SERVICE:
                    Toast.makeText(SimpleSearchFragment.this.getActivity(), "onStatusChanged:当前GPS状态为服务区外状态", Toast.LENGTH_SHORT).show();
                    break;
                //GPS状态为暂停服务时
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Toast.makeText(SimpleSearchFragment.this.getActivity(), "onStatusChanged:当前GPS状态为暂停服务状态", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(SimpleSearchFragment.this.getActivity(), "onProviderEnabled:方法被触发", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
