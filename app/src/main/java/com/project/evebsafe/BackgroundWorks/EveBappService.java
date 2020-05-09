package com.project.evebsafe.BackgroundWorks;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.project.evebsafe.Database.SharedPreference;

import java.util.List;
import java.util.Locale;

public class EveBappService extends Service {
    SharedPreference preference;
    LocationManager locationManager;
    GPS_TRACKER tracker;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        preference=new SharedPreference(this);
        final Handler handler=new Handler();//For synchronus task this object is needed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               handler.postDelayed(this,20000);
               if(preference.isRunning()){

                   if (isGPSEnabled()){
                       tracker=new GPS_TRACKER(EveBappService.this);
                       getCompleteAddress(tracker.getLat(),tracker.getLongt());

                   }else{

                       Toast.makeText(EveBappService.this, "Turn on Location", Toast.LENGTH_SHORT).show();

                   }
               }
                else
               {
                   handler.removeCallbacks(this);
                   Toast.makeText(EveBappService.this, "Apps  is not Running", Toast.LENGTH_SHORT).show();

               }
            }
        }, 20000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public boolean isGPSEnabled(){
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
    public void getCompleteAddress(double latitude,double longtitude)
    {
        try{
            Geocoder geocoder=new Geocoder(this, Locale.getDefault());
            List<Address>addresses=geocoder.getFromLocation(latitude,longtitude,1);
            String  street=addresses.get(0).getAddressLine(0);
            String city=addresses.get(0).getLocality();
            String zip=addresses.get(0).getPostalCode();
            String country=addresses.get(0).getCountryName();
            Toast.makeText(this, street+" "+city+" "+zip+" "+country, Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {

        }

    }
}
