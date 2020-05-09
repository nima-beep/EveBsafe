package com.project.evebsafe.BackgroundWorks;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

public class GPS_TRACKER implements LocationListener {
    Context context;
    Location location;
    double latitude;


    double longtitude;
    LocationManager locationManager;
    private static final long mimimum_distance_update = 10;
    private static final long mimimum_time_update = 10000;

    public GPS_TRACKER(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (isGPSEnabled()) {
            setLocation(LocationManager.GPS_PROVIDER);

        }
        if (isNetworkEnabled()) {
            setLocation(LocationManager.NETWORK_PROVIDER);
        }
    }

    public void setLocation(String provider) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //runtime permission check
                locationManager.requestLocationUpdates(provider, mimimum_time_update, mimimum_distance_update, this);
                location=locationManager.getLastKnownLocation(provider);
            }
        }else {
            locationManager.requestLocationUpdates(provider, mimimum_time_update, mimimum_distance_update, this);
            location=locationManager.getLastKnownLocation(provider);
        }
        if(locationManager!=null && location!=null){
            setLatitude(location.getLatitude());
            setLongtitude(location.getLongitude());
        }

    }

    public boolean isNetworkEnabled(){
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    public boolean isGPSEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }


    public double getLat() {
        if(location!=null)
        {
            latitude=location.getLatitude();

        }
        return latitude;
    }

    public double getLongt() {
        if(location!=null)
        {
          longtitude=location.getLongitude();

        }
        return longtitude;
    }

    @Override
    public void onLocationChanged(Location location) {

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


}
