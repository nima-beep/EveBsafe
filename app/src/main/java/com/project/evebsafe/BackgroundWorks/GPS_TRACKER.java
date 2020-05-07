package com.project.evebsafe.BackgroundWorks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPS_TRACKER implements LocationListener {
    Context context;
    boolean isGPS_ENABLED=false;
    boolean is_NETWORK_ENABLED=false;
    boolean canGetLocation=false;
    Location location;
    double latitude;
    double longtitude;
    LocationManager locationManager;
    private static final long mimimum_distance_update=10;
    private static final long mimimum_time_update=60000;

    public GPS_TRACKER(Context context) {
        this.context=context;
        getLocation();
    }
    @SuppressLint("MissingPermission")
    public Location getLocation()
    {
        try {

            locationManager= (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            isGPS_ENABLED=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            is_NETWORK_ENABLED=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(isGPS_ENABLED && is_NETWORK_ENABLED)
            {
                canGetLocation=true;
                if(is_NETWORK_ENABLED)
                {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,mimimum_time_update,mimimum_distance_update,this);
                    location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
                if (isGPS_ENABLED)
                {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,mimimum_time_update,mimimum_distance_update,this);
                    location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }


            }
            else
            {

            }

        }catch (Exception e)
        {

        }

        return location;
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
