package com.project.evebsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.accounts.AbstractAccountAuthenticator;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Dialogboxes.LockPattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    SharedPreference preference;
    Button Register;
    EditText EName,Enumber,Eaddress,Eemail;
    TextView alreadyregistered;
    String Name,Email,Address,Phone;
    LockPattern lockPattern;
    DrawerLayout drawerLayout;
    NavigationView  navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference=new SharedPreference(this);
        if (!preference.isRegistered()){
            if(preference.isLocked()){
                lockPattern=new LockPattern(this);
            }
            setContentView(R.layout.activity_main);
            drawerLayout=findViewById(R.id.drawerLayout);
            navigationView=findViewById(R.id.navigationview);
            toggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.Open,R.string.Close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mynavigationview();



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
                        Toast.makeText(MainActivity.this, "number set pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.meaasageSet:
                        Toast.makeText(MainActivity.this, "message set pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.timeSet:
                        Toast.makeText(MainActivity.this, "time set pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.patternSet:
                        Toast.makeText(MainActivity.this, "pattern set pressed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.lockstate:
                        if (preference.isLocked()){

                            menuItem.setTitle("Unlocked");
                            menuItem.setIcon(R.drawable.lockopen);
                            preference.lockState(false);

                        }else {
                            menuItem.setTitle("Locked");
                            menuItem.setIcon(R.drawable.lockclose);
                            preference.lockState(true);


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


}
