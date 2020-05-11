package com.project.evebsafe.BackgroundWorks;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.R;

import java.util.List;
import java.util.Locale;

public class EveBappService extends Service {
    SharedPreference preference;
    LocationManager locationManager;
    GPS_TRACKER tracker;
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    ImageView imageView;
    Dialog dialog;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
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

                      if(dialog==null)
                      {
                          alertWindow();
                      }
                      else if(!dialog.isShowing())
                      {

                          alertWindow();
                      }

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
    public void init() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {   //for showing alert window on top
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(     //for showing alert window on top
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        }
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        imageView = new ImageView(this);//Window manager's initial view
        imageView.setVisibility(View.GONE);

        params.gravity = Gravity.TOP | Gravity.CENTER;
        params.x = ((getApplicationContext().getResources().getDisplayMetrics().widthPixels) / 2);
        params.y = ((getApplicationContext().getResources().getDisplayMetrics().heightPixels) / 2);
        windowManager.addView(imageView, params);
    }
    public void alertWindow() {
        final TextView cancel,settings;


        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        dialog.getWindow().setDimAmount(0);
        dialog.setContentView(R.layout.alertwindow);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        cancel=dialog.findViewById(R.id.cancelid);
        settings=dialog.findViewById(R.id.settingsid);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });



    }
}
