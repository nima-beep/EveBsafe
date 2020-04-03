package com.project.evebsafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.project.evebsafe.Linkers.Backtrack;
import com.project.evebsafe.Linkers.CancelListener;
import com.project.evebsafe.menuoptions.PatternConfirm;
import com.project.evebsafe.menuoptions.PatternSet;
import com.project.evebsafe.menuoptions.Timeset;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Backtrack, CancelListener {

    SharedPreference preference;
    Button Register;
    EditText EName,Enumber,Eaddress,Eemail;
    TextView alreadyregistered;
    String Name,Email,Address,Phone;
    LockPattern lockPattern;
    DrawerLayout drawerLayout;
    NavigationView  navigationView;
    ActionBarDrawerToggle toggle;
    FragmentManager fragmentManager;
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
    }


    @Override
    public void onComplete() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }


    }

}
