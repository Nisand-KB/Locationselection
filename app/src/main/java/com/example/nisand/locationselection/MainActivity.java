package com.example.nisand.locationselection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LocationListener {
    Button b1;
    private LocationManager locationManager;
    private double longitude;
    private double latitude;
    Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.button);
        b1.setOnClickListener(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button){
            //location
            onLocationChanged(location);
            loc_fun(location);

            // Toast.makeText(getApplicationContext(),"Button 1",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude  = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    private void loc_fun(Location location){
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses=null;
            addresses=geocoder.getFromLocation(latitude,longitude,1);
            String country = addresses.get(0).getCountryName();
            String city =  addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            //textView.setText("Country" + country );

            Toast.makeText(getApplicationContext(),country+"  "+city+" "+state,Toast.LENGTH_SHORT).show();
//
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error: "+e,Toast.LENGTH_SHORT).show();
        }
    }
}