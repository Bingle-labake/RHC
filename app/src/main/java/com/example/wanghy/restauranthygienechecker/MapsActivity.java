package com.example.wanghy.restauranthygienechecker;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.wanghy.restauranthygienechecker.entity.Business;
import com.example.wanghy.restauranthygienechecker.entity.SearchList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude = 0,longitude = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        Intent i = getIntent();
        SearchList searchList = (SearchList) i.getSerializableExtra("searchList");
//        latitude = i.getDoubleExtra("latitude", -1);
//        longitude = i.getDoubleExtra("longitude", -1);
//        latitude = i.getDoubleExtra("latitude1", -1);
//        longitude = i.getDoubleExtra("longitude1", -1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Log.d("la", latitude+"," +longitude);
        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-1.935762, 52.450478);
        mMap.addMarker(new MarkerOptions().position(sydney).title("You location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your location!"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),15));
    }
}
