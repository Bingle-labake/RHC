package com.example.wanghy.restauranthygienechecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_page);
        Intent i = getIntent();
        Business est = (Business) i.getSerializableExtra("establishment");

        TextView ratingValue = (TextView) findViewById(R.id.ratingValue);
        ratingValue.setText(est.getRatingValue()+"");
        TextView addressLine = (TextView) findViewById(R.id.addressLine);
        addressLine.setText(est.getAddressLine()+"");
        TextView phone = (TextView) findViewById(R.id.phone);
        phone.setText(est.getPhone()+"");
        TextView businessName = (TextView) findViewById(R.id.businessName);
        businessName.setText(est.getBusinessName()+"");

        longitude = Double.parseDouble(est.getLongitude());
        latitude = Double.parseDouble(est.getLatitude());




        Log.d("--------Est------", est.getBusinessName());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your location!"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),15));
    }
}
