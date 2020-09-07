package com.project.evebsafe.BackgroundWorks;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Database.UserInfo;
import com.project.evebsafe.Model.LocationState;
import com.project.evebsafe.Model.RegistrationState;
import com.project.evebsafe.Network.ApiClient;
import com.project.evebsafe.Network.ApiService;
import com.project.evebsafe.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EveBappService extends Service {
    SharedPreference preference;
    double  pLat=180,pLongt=360;
    UserInfo userInfo;
    LocationManager locationManager;
    GPS_TRACKER tracker;
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    ImageView imageView;
    ArrayList<String>numbers;
    SmsManager smsManager;
    Date date;
    SimpleDateFormat  simpleDateFormat;

    long hr,min,sec,totaltime;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        userInfo=new UserInfo(this);
        numbers=userInfo.allNumber();
        smsManager=SmsManager.getDefault();

       //init();
        locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        preference=new SharedPreference(this);
        sec=preference.getSecond()*1000;
        min=preference.getMinute()*60*1000;
        hr=preference.getHour()*60*1000*60;
        totaltime=sec+min+hr;

        final Handler handler=new Handler();//For synchronus task this object is needed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               handler.postDelayed(this,totaltime);
               if(preference.isRunning()){

                   if (isGPSEnabled()) {
                       tracker = new GPS_TRACKER(EveBappService.this);
                       getCompleteAddress(tracker.getLat(), tracker.getLongt());

                   }
               }
                else
               {
                   handler.removeCallbacks(this);


               }
            }
        }, totaltime);
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

        date=new Date();
        simpleDateFormat=new SimpleDateFormat("dd-MMMM-yy hh:mm aa");
        String time= simpleDateFormat.format(date);
        try{
            Geocoder geocoder=new Geocoder(this, Locale.getDefault());
            List<Address>addresses=geocoder.getFromLocation(latitude,longtitude,1);
            String  street=addresses.get(0).getAddressLine(0);
            String city=addresses.get(0).getLocality();
            String zip=addresses.get(0).getPostalCode();
            String country=addresses.get(0).getCountryName();
            Toast.makeText(this, "show message", Toast.LENGTH_SHORT).show();


                networkCall(preference.getPhone(),preference.getEmail(),city,street,country,zip,time);
                for (int i=0;i<numbers.size();i++)
                {
                    smsManager.sendTextMessage(numbers.get(i),null,preference.getMessage()+" "+street+" "+city+" "+zip+" "+time,null,null);

                }
                pLongt=longtitude;
                pLat=latitude;




        }catch (Exception e)
        {

        }

    }
    public void init() {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {   //for showing alert window on top
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(     //for showing alert window on top
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
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

        preference.setVisibility(true);
        final Dialog  dialog = new Dialog(this);
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
                preference.setVisibility(false);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
                preference.setVisibility(false);
            }
        });



    }

    public void networkCall(String phonenumber,String email,String city,String street,String country,String zip,String time)
    {


        ApiService apiService= ApiClient.getClient().create(ApiService.class);
        Call<LocationState> call=apiService.locationinsert(phonenumber,email,city,street,country,zip,time);
        call.enqueue(new Callback<LocationState>() {
            @Override
            public void onResponse(Call<LocationState> call, Response<LocationState> response) {

            }

            @Override
            public void onFailure(Call<LocationState> call, Throwable t) {

            }
        });
    }
}
