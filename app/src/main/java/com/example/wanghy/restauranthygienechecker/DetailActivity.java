package com.example.wanghy.restauranthygienechecker;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wanghy.restauranthygienechecker.entity.Business;
import com.example.wanghy.restauranthygienechecker.entity.Establishment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by wanghy on 2018/3/5.
 */

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private double latitude = 0,longitude = 0;

    private SharedPreferences sharedPref;
    private Library db;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);

        Intent i = getIntent();
        final Business est = (Business) i.getSerializableExtra("establishment");

        TextView ratingValue = (TextView) findViewById(R.id.ratingValue);
        ratingValue.setText(est.getRatingValue()+"");
        TextView addressLine = (TextView) findViewById(R.id.addressLine);
        addressLine.setText(est.getAddressLine()+"");
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(est.getPhone()+"");
        final TextView businessName = (TextView) findViewById(R.id.businessName);
        businessName.setText(est.getBusinessName()+"");
//        TextView distance = (TextView) findViewById(R.id.distance);
//        distance.setText(est.getDistance()+"");
//        TextView email = (TextView) findViewById(R.id.tv_email);
//        email.setText(est.getEmail()+"");
//        TextView web = (TextView) findViewById(R.id.tv_web);
//        web.setText(est.getweb()+"");


        longitude = Double.parseDouble(est.getLongitude());
        latitude = Double.parseDouble(est.getLatitude());




        Log.d("--------Est------", est.getBusinessName());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        final Button like=(Button) this.findViewById(R.id.btn_collect);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        db = Room.databaseBuilder(getApplicationContext(),Library.class, "business-library").allowMainThreadQueries().build();


        like.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                Log.v("MyTag","onClick");
                db.BusinessDao().insertBusiness(est);
                like.setBackgroundResource(R.drawable.ic_like);
            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your location!"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),15));
    }
}
