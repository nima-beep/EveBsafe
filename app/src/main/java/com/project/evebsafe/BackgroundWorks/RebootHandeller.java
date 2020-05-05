package com.project.evebsafe.BackgroundWorks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RebootHandeller extends BroadcastReceiver {
    Intent  intent1;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            intent1=new Intent(context,EveBappService.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//This is use to force the os to execute the task(starting service/class)
             context.startService(intent1);
        }
    }
}
