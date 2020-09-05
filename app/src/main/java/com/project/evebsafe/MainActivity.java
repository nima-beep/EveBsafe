package com.project.evebsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.project.evebsafe.Adapters.CustomListViewAdapters;
import com.project.evebsafe.BackgroundWorks.EveBappService;
import com.project.evebsafe.BackgroundWorks.ShakeEventDetector;
import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Database.UserInfo;
import com.project.evebsafe.Dialogboxes.LockPattern;
import com.project.evebsafe.Dialogboxes.ShowMessage;
import com.project.evebsafe.Linkers.Backtrack;
import com.project.evebsafe.Linkers.CancelListener;
import com.project.evebsafe.Linkers.DeleteHandeller;
import com.project.evebsafe.Linkers.ShowInterface;
import com.project.evebsafe.Linkers.ShowTime;
import com.project.evebsafe.Model.RegistrationState;
import com.project.evebsafe.Network.ApiClient;
import com.project.evebsafe.Network.ApiService;
import com.project.evebsafe.menuoptions.LocationDetails;
import com.project.evebsafe.menuoptions.PatternConfirm;
import com.project.evebsafe.menuoptions.PatternSet;
import com.project.evebsafe.menuoptions.SetMessage;
import com.project.evebsafe.menuoptions.SetNumber;
import com.project.evebsafe.menuoptions.Timeset;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Backtrack, CancelListener, DeleteHandeller, ShowInterface, ShowTime {


    SharedPreference preference;
    Button Register;
    EditText EName,Enumber,Eaddress,Eemail;
    TextView alreadyregistered,textView,hr,min,sec,msg;
    ListView listView;
    String Name,Email,Address,Phone;
    LockPattern lockPattern;
    DrawerLayout drawerLayout;
    Switch sw;
    UserInfo userInfo ;
    DeleteHandeller deleteHandeller;
    CustomListViewAdapters customListViewAdapters;
    NavigationView  navigationView;
    ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager;
    Dialog dialog;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference=new SharedPreference(this);
        userInfo=new UserInfo(this);
        intent=new Intent(this, EveBappService.class);
        if (preference.isRegistered()){
            if(preference.isLocked()){
                lockPattern=new LockPattern(this);
            }
            setContentView(R.layout.activity_main);
            deleteHandeller=this;

            hr=findViewById(R.id.showhr);
            min=findViewById(R.id.showmin);
            sec=findViewById(R.id.showsec);
            msg=findViewById(R.id.showMessageId);

            hr.setText(String.valueOf(preference.getHour())+" hr");
            min.setText(String.valueOf(preference.getMinute())+" min");
            sec.setText(String.valueOf(preference.getSecond())+" sec");
            msg.setText(preference.getMessage());


            listView=findViewById(R.id.listviewId);
            drawerLayout=findViewById(R.id.drawerLayout);
            navigationView=findViewById(R.id.navigationview);
            toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            showList();
            mynavigationview();

            ShakeEventDetector shakeEventDetector=new ShakeEventDetector(this);
            shakeEventDetector.setOnShakeEventListener(new ShakeEventDetector.OnShakeListener() {
                @Override
                public void OnShake(int count) {
                    Toast.makeText(MainActivity.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
                }
            });



        }else{
            setContentView(R.layout.registrationwindow);

            Register=findViewById(R.id.registerbutton);
            alreadyregistered=findViewById(R.id.alreadyregistered);
            EName=findViewById(R.id.editname);
            Enumber=findViewById(R.id.editnumber);
            Eaddress=findViewById(R.id.editaddress);
            Eemail=findViewById(R.id.editemail);
            Register.setOnClickListener(this);
            alreadyregistered.setOnClickListener(this);
        }

    }

    public void mynavigationview(){
        MenuItem item=  navigationView.getMenu().findItem(R.id.lockstate);
        View view=navigationView.getHeaderView(0);
        textView =view.findViewById(R.id.usernameid);
        sw=view.findViewById(R.id.runningStateid);
        textView.setText(preference.getNames());
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Runtime permission check for marshmallow and upper version
                if(isChecked)
                {
                    if (!hasAlertWindow())
                    {
                        Disable();
                        //taking permission for alert window
                        Intent checkIntent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
                        startActivity(checkIntent);
                    }else{
                        permissionChecker();

                    }
                }
                else
                {
                    Disable();
                }

            }
        });

        if(preference.isRunning()){

           sw.setText("Enabled");
            sw.setChecked(true);
            startService(intent);
        }
        else
        {
            sw.setText("Disabled");
            sw.setChecked(false);

        }
        if(preference.isLocked()){

          item.setTitle("Locked");
          item.setIcon(R.drawable.lockclose);
        }
        else
        {
            item.setTitle("Unlocked");
            item.setIcon(R.drawable.lockopen);

        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.showlocation:
                        locationDetails();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.numberSet:
                       setNumber();
                       drawerLayout.closeDrawers();
                        break;
                    case R.id.meaasageSet:
                       setMessage();
                       drawerLayout.closeDrawers();
                        break;
                    case R.id.timeSet:
                    timeset();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.patternSet:
                        patternset();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.lockstate:
                        if (preference.isLocked()){

                            menuItem.setTitle("Unlocked");
                            menuItem.setIcon(R.drawable.lockopen);
                            preference.lockState(false);

                        }else {

                            if(preference.getPattern().equals(""))
                            {
                                drawerLayout.closeDrawers();
                                patternset();

                            }
                            else
                            {
                                menuItem.setTitle("Locked");
                                menuItem.setIcon(R.drawable.lockclose);
                                preference.lockState(true);

                            }



                        }
                        break;

                }
                return true;
            }
        }) ;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View v) {//
        switch (v.getId()){
            case R.id.registerbutton://registration is complete intially
               register();
                break;
            case R.id.alreadyregistered:
                Alreadyregistered();
                break;
        }

    }

    public void register(){
        Name=EName.getText().toString();
      Phone=Enumber.getText().toString();
      Address=Eaddress.getText().toString();
      Email=Eemail.getText().toString();

      if (Name.isEmpty()){
          Toast.makeText(this, "Fill up your Name", Toast.LENGTH_SHORT).show();
      }
      else
      {
          if(Phone.isEmpty()){
             Toast .makeText(this, "Insert your Number", Toast.LENGTH_SHORT).show();

          }
          else
          {

              if(Address.isEmpty()) {
                  Toast.makeText(this, "Insert your ADDRESS", Toast.LENGTH_SHORT).show();
              }
             else{

                 if(Email.isEmpty()){
                     Toast.makeText(this, "Insert your Email", Toast.LENGTH_SHORT).show();
                 }
                 else
                 {

                     postToServer(Name,Phone,Address,Email);//sending data to server
                 }
             }
          }
       }
    }


public void postToServer(final String Name, final String PhoneNumber, final String Address, final String Email){
    ApiService apiService= ApiClient.getClient().create(ApiService.class);//Retrofit library for  mobilenetwork for connecting server
    Call<RegistrationState>call=apiService.registration(Name,PhoneNumber,Address,Email);//part of API calling,in Network-ApiService

    call.enqueue(new Callback<RegistrationState>() {//server calling method
        @Override
        public void onResponse(Call<RegistrationState> call, Response<RegistrationState> response) {//regiatration is used for mapping the server response,Model-registrationState
            switch (response.body().getStatusCode())
            {

                case 0:
                    Toast.makeText(MainActivity.this, "User already exits", Toast.LENGTH_SHORT).show();
                    break;
                case  1:
                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    //data will save in sharedPreference
                    preference.saveUser(Name,PhoneNumber,Address,Email);
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                    break;

                case -1:
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        @Override
        public void onFailure(Call<RegistrationState> call, Throwable t) {

        }
    });


}
public void patternset(){
     Backtrack  backtrack=this;
    PatternSet patternSet=new PatternSet(this,backtrack);
    fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.rootLayout,patternSet);
    fragmentTransaction.addToBackStack("patternSet");
    fragmentTransaction.commit();


}
public void patternconfirm(String samestring){
        CancelListener cancelListener=this;
    PatternConfirm patternConfirm=new PatternConfirm(this,samestring,cancelListener);
    fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.rootLayout,patternConfirm);
    fragmentTransaction.addToBackStack("patternConfirm");
    fragmentTransaction.commit();



}
public void timeset(){
    Backtrack  backtrack=this;
    ShowTime showTime=this;
    Timeset timeset=new Timeset(this,backtrack,showTime);
    fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.rootLayout,timeset);
    fragmentTransaction.addToBackStack("timeset");
    fragmentTransaction.commit();

}

    @Override
    public void onNext(String samestring)
    {
        patternconfirm(samestring);
    }

    @Override
    public void onCancel() {
        getSupportFragmentManager().popBackStack();
        showList();
    }


    @Override
    public void onComplete() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }


    }
    public void setMessage(){
        ShowInterface showInterface=this;
        SetMessage setMessage=new SetMessage(this,showInterface);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();//start work
        fragmentTransaction.replace(R.id.rootLayout,setMessage);//replace with home screen
        fragmentTransaction.addToBackStack("SetMessage");
        fragmentTransaction.commit();//submit

    }

    public  void setNumber(){
        Backtrack backtrack=this;

        SetNumber setNumber=new SetNumber(this,backtrack);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rootLayout,setNumber);
        fragmentTransaction.addToBackStack("SetNumber");
        fragmentTransaction.commit();


    }
    public void showList(){
        ArrayList<ArrayList<String>>data=new ArrayList<>();
        data=userInfo.allInfo();

        customListViewAdapters=new CustomListViewAdapters(this,data.get(0),data.get(1),data.get(2),deleteHandeller,userInfo);
        listView.setAdapter(customListViewAdapters);




    }


    @Override
    public void delete() {//To refresh the list
        showList();

    }
    //runtime permission
    public void permissionChecker(){
        if(hasFineLocation() &  hasSmsPermission()){
            Enable();
        }
        else if(hasFineLocation() && !hasSmsPermission()){
            ActivityCompat.requestPermissions(this,new String[]{SEND_SMS},1);

        }
        else if(hasSmsPermission() & !hasFineLocation()){
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},2);

        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION, SEND_SMS},3);
        }
    }
   public  boolean hasFineLocation()
    {
       return  (ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED);
    }

    public  boolean hasSmsPermission()
    {
        return  (ContextCompat.checkSelfPermission(getApplicationContext(),SEND_SMS)== PackageManager.PERMISSION_GRANTED);
    }

    public  boolean hasAlertWindow()
    {//permission for upper marshmallow
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            return Settings.canDrawOverlays(this);
        }else{
            return true;
        }
    }


    public  void Enable()
    {

       if(!isGPSEnabled())
       {

           alertWindow();
       }

           Toast.makeText(MainActivity.this, "Enabled", Toast.LENGTH_SHORT).show();
           sw.setText("Enabled");
           preference.saverunningState(true);
           startService(intent);



    }
    public void Disable()
    {


        Toast.makeText(MainActivity.this, "Disabled", Toast.LENGTH_SHORT).show();
        sw.setText("Disabled");
        sw.setChecked(false);
        preference.saverunningState(false);
        stopService(intent);
    }

//Result of user interaction
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            //here [0] ,because the element in permissionchecke() method
            case 1:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Enable();
                }else {
                    Disable();
                }
                break;

            case 2:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Enable();
                }else {
                    Disable();
                }
                break;
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED)


                {
                    Enable();
                }else {
                    Disable();
                }
                break;

        }
    }
    public void alertWindow() {
        final TextView cancel, settings;


        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        dialog.getWindow().setDimAmount(0);
        dialog.setContentView(R.layout.alertwindow);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        cancel = dialog.findViewById(R.id.cancelid);
        settings = dialog.findViewById(R.id.settingsid);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
    }

    public boolean isGPSEnabled(){
        LocationManager locationManager=(LocationManager)getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
public void Alreadyregistered()
{



    Dialog dialog1=new Dialog(this);
    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog1.setContentView(R.layout.alreadyinserted);
    dialog1.show();
    Button  button1;
    final EditText editText1,editText2;

    button1=dialog1.findViewById(R.id.newButton);
    editText1=dialog1.findViewById(R.id.newPnNumber);
    editText2=dialog1.findViewById(R.id.newEmail);

    button1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

           networkCall(editText1.getText().toString(),editText2.getText().toString());
        }
    });



}

public  void networkCall(String phonenumber,String email)
{
    ApiService apiService= ApiClient.getClient().create(ApiService.class);
    Call<RegistrationState>call=apiService.registeredalready(phonenumber,email);

    call.enqueue(new Callback<RegistrationState>() {
        @Override
        public void onResponse(Call<RegistrationState> call, Response<RegistrationState> response) {

            switch (response.body().getStatusCode())
            {

                case 0:
                    Toast.makeText(MainActivity.this, "User already exits", Toast.LENGTH_SHORT).show();
                    break;
                case  1:
                    Toast.makeText(MainActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    //received data for alreadyregistered will be saved in sharedPreference
                    RegistrationState state=response.body();
                    preference.saveUser(state.getName(),state.getPhoneNumber(),state.getAddress(),state.getEmail());
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                    finish();
                    break;

                case -1:
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        @Override
        public void onFailure(Call<RegistrationState> call, Throwable t) {

        }
    });

}

public void locationDetails()
{
    LocationDetails locationDetails=new  LocationDetails(preference);
    fragmentManager=getSupportFragmentManager();
    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.rootLayout, locationDetails);
    fragmentTransaction.addToBackStack("LocationDetails");
    fragmentTransaction.commit();
}

    @Override
    public void refresh() {
        msg.setText(preference.getMessage());
    }

    @Override
    public void refreshtime() {
        hr.setText(String.valueOf(preference.getHour())+" hr");
        min.setText(String.valueOf(preference.getMinute())+" min");
        sec.setText(String.valueOf(preference.getSecond())+" sec");


    }
}

