package com.project.evebsafe.BackgroundWorks;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.project.evebsafe.Database.SharedPreference;

public class EveBappService extends Service {
    SharedPreference preference;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preference=new SharedPreference(this);
        final Handler handler=new Handler();//For synchronus task this object is needed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

               handler.postDelayed(this,10000);
               if(preference.isRunning()){
                   Toast.makeText(EveBappService.this, "Apps Running", Toast.LENGTH_SHORT).show();
               }
                else
               {
                   handler.removeCallbacks(this);
                   Toast.makeText(EveBappService.this, "Apps  is not Running", Toast.LENGTH_SHORT).show();

               }
            }
        }, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
