package com.project.evebsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.accounts.AbstractAccountAuthenticator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.project.evebsafe.menuoptions.PatternConfirm;
import com.project.evebsafe.menuoptions.PatternSet;
import com.project.evebsafe.menuoptions.SetMessage;
import com.project.evebsafe.menuoptions.SetNumber;
import com.project.evebsafe.menuoptions.Timeset;

import java.util.ArrayList;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Backtrack, CancelListener, DeleteHandeller {

    SharedPreference preference;
    Button Register;
    EditText EName,Enumber,Eaddress,Eemail;
    TextView alreadyregistered,textView;
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
    FloatingActionButton floatingActionButton;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference=new SharedPreference(this);
        userInfo=new UserInfo(this);
        intent=new Intent(this, EveBappService.class);
        if (!preference.isRegistered()){
            if(preference.isLocked()){
                lockPattern=new LockPattern(this);
            }
            setContentView(R.layout.activity_main);
            deleteHandeller=this;

            floatingActionButton=findViewById(R.id.fab);

            listView=findViewById(R.id.listviewid);
            drawerLayout=findViewById(R.id.drawerLayout);
            navigationView=findViewById(R.id.navigationview);
            toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            showList();
            mynavigationview();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShowMessage showMessage=new ShowMessage(MainActivity.this,preference);

                }
            });
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
                    if(hasCoarseLocation()&&hasFineLocation())
                    {
                       Enable();
                    }
                    else if(!hasCoarseLocation() &&hasFineLocation())
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_COARSE_LOCATION},1);
                    }
                    else if(hasCoarseLocation() &&!hasFineLocation())
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_FINE_LOCATION},2);
                    }

                    else
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION},3);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerbutton:
               register();
                break;
            case R.id.alreadyregistered:
                Toast.makeText(this, "Already Registered", Toast.LENGTH_SHORT).show();
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

                     postToServer(Name,Phone,Address,Email);
                 }
             }
          }
       }
    }


public void postToServer(String a,String b,String c,String d){


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
    Timeset timeset=new Timeset(this,backtrack);
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

     SetMessage setMessage=new SetMessage(this);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rootLayout,setMessage);
        fragmentTransaction.addToBackStack("SetMessage");
        fragmentTransaction.commit();

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
    public void delete() {
        showList();

    }
    public void permissionChecker()
    {

    }
    public  boolean hasFineLocation()
    {
       return  (ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED);
    }
    public  boolean hasCoarseLocation()
    {
        return  (ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED);
    }
    public  boolean hasNetworkState()
    {
        return  (ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_NETWORK_STATE)== PackageManager.PERMISSION_GRANTED);
    }
    public  void Enable()
    {

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
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Enable();
                }
                else
                {
                    Disable();
                }
                break;
            case 2:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    Enable();

                }
                else
                {
                    Disable();
                }
                break;

            case 3:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED &&grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    Enable();
                }
                else
                {
                    Disable();
                }
                break;



        }
    }
}

