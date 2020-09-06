package com.project.evebsafe.BackgroundWorks;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeEventDetector implements SensorEventListener {


    private OnShakeListener listener;//customized method
    private static final float GRAVITY_THRESHOLD=2.5f;
    private static final int TIME_SLOP_MS=500;
    private static final int TIME_TO_RESET=3000;
    private int shakeCount;
    private long shakeTimeStamp=0;
    private SensorManager sensorManager;

    public ShakeEventDetector(Context context) {
        sensorManager=(SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);

    }

    public void setOnShakeEventListener(OnShakeListener listener)
    {
        this.listener=listener;

    }



    public interface OnShakeListener{
        void OnShake(int count);

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(listener!=null)
        {
            float x,y,z,gx,gy,gz,geforce;
            x=event.values[0];
            y=event.values[1];
            z=event.values[2];

            gx=x/SensorManager.GRAVITY_EARTH;
            gy=y/SensorManager.GRAVITY_EARTH;
            gz=z/SensorManager.GRAVITY_EARTH;
            geforce= (float)Math.sqrt(gx*gx+gy*gy+gz*gz);
            if(geforce>GRAVITY_THRESHOLD)
            {
                final long currenTime=System.currentTimeMillis();

                if(shakeTimeStamp+TIME_SLOP_MS>currenTime)
                {

                    return;
                }

                if(shakeTimeStamp+TIME_TO_RESET<currenTime)
                {
                        shakeCount=0;
                }
                shakeTimeStamp=currenTime;
                shakeCount++;
                listener.OnShake(shakeCount);

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
