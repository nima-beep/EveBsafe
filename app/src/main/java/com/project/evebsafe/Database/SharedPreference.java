package com.project.evebsafe.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    public SharedPreference(Context context1) {
        this.context=context1;
    }

    public void saveUser(String name,String phone,String address,String email ){
        preferences=context.getSharedPreferences(Constants.APPS_PREFERENCE,Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putString(Constants.NAME,name);
        editor.putString(Constants.PHONE,phone);
        editor.putString(Constants.ADDRESS,address);
        editor.putString(Constants.EMAIL,email);
        editor.commit();

    }
    public void saverunningState(boolean variable)
    {
        preferences=context.getSharedPreferences(Constants.APPS_PREFERENCE,Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putBoolean(Constants.RUNNING_STATE,variable);

        editor.commit();

    }

    public void lockState(boolean variable)
    {
        preferences=context.getSharedPreferences(Constants.APPS_PREFERENCE,Context.MODE_PRIVATE);
        editor=preferences.edit();
        editor.putBoolean(Constants.LOCK_STATE,variable);
        editor.commit();

    }

    public void savePattern(String variable ) {
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString(Constants.PATTERN, variable);

        editor.commit();
    }

    public boolean isRunning(){
    preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
    return preferences.getBoolean(Constants.RUNNING_STATE,false);

    }

    public boolean isLocked(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(Constants.LOCK_STATE,false);

    }

    public String getPattern(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(Constants.PATTERN,"");

    }
    public String getNames(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(Constants.NAME,"");

    }


    public String getAddress(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(Constants.ADDRESS,"");

    }

    public String getEmail(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(Constants.EMAIL,"");

    }

    public String getPhone()
    {
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(Constants.PHONE,"");

    }

    public boolean isRegistered(){
        String  name,phone;
        name=getNames();
        phone=getPhone();
        if(name.isEmpty()&&phone.isEmpty())
        {
           return false;

        }
        else
        {
            return true;
        }


    }
    public int getHour(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(Constants.HOUR,0);

    }
    public int getMinute(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(Constants.MINUTE,0);

    }
    public int getSecond(){
        preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getInt(Constants.SECOND,0);

    }
   public void saveTime(int H,int M,int S){
       preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
       editor = preferences.edit();
       editor.putInt(Constants.HOUR,H );
       editor.putInt(Constants.MINUTE,M );
       editor.putInt(Constants.SECOND,S );

       editor.commit();



   }
   public void saveMessage(String msg){
       preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
       editor = preferences.edit();
       editor.putString(Constants.MESSAGE,msg );


       editor.commit();

   }
   public String getMessage(){
       preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
       return preferences.getString(Constants.MESSAGE,"");


   }
   public void setVisibility(boolean value)
   {
       preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
       editor = preferences.edit();
       editor.putBoolean(Constants.VISIBILITY,value);

       editor.commit();
   }

   public boolean isShown()
   {
       preferences = context.getSharedPreferences(Constants.APPS_PREFERENCE, Context.MODE_PRIVATE);
       return preferences.getBoolean(Constants.VISIBILITY,false);
   }
}
