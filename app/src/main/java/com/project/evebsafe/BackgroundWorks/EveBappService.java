package com.project.evebsafe.BackgroundWorks;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class EveBappService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final Handler handler=new Handler();//For synchronus task this object is needed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               handler.postDelayed(this,10000);
                Toast.makeText(EveBappService.this, "Apps Running", Toast.LENGTH_SHORT).show();
            }
        }, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
