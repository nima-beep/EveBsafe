package com.project.evebsafe.BackgroundWorks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.project.evebsafe.Database.SharedPreference;

public class RebootHandeller extends BroadcastReceiver {
    Intent  intent1;
    SharedPreference preference;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            preference=new SharedPreference(context);
            if(preference.isRunning())
            {
                intent1=new Intent(context,EveBappService.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//This is use to force the os to execute the task(starting service/class)
                context.startService(intent1);

            }

        }

    }
}
