package com.example.wanghy.restauranthygienechecker;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.wanghy.restauranthygienechecker.common.StringRequestWithFood;
import com.example.wanghy.restauranthygienechecker.entity.BitmapCache;
import com.example.wanghy.restauranthygienechecker.entity.Business;
import com.example.wanghy.restauranthygienechecker.entity.Establishment;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.location.LocationManager.NETWORK_PROVIDER;


/**
 * Created by wanghy on 2018/2/26.
 */

public class FavoriteFragment extends Fragment {

    View myView;
    private ListView lv;
    RequestQueue requestQueue;
    private Library db;



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
        myView = inflater.inflate(R.layout.favorite, null);
        initViews(myView);

        return myView;



    }

    private void initViews(View view) {
        lv = (ListView) view.findViewById(R.id.listViewF);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("onclick", "clicked"+i);
//                Intent ii = new Intent(getActivity(), DetailActivity.class);
//                ii.putExtra("establishment",adapter.getEst(i) );
//                startActivity(ii);
//            }
//        });

        reLoadData();
    }



    private void reLoadData() {
        String address = "Aster House";//定位获取地址

        db = Room.databaseBuilder(getContext(),Library.class, "business-library").allowMainThreadQueries().build();

        ArrayList<Business> list =(ArrayList<Business>) db.BusinessDao().getAll();
        Log.d("-----------", ""+list.size());
        searchlist = list;
        adapter = new BusinessListAdapter(list);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        Log.e(TAG, "longitude and latitude: " + lngandlat + "\n");
    }

}
