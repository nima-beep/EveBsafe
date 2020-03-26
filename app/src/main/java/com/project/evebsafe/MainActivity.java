package com.project.evebsafe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Dialogboxes.LockPattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    SharedPreference preference;
    Button Register;
    EditText EName,Enumber,Eaddress,Eemail;
    TextView alreadyregistered;
    String Name,Email,Address,Phone;
    LockPattern lockPattern;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference=new SharedPreference(this);
        if (preference.isRegistered()){
            if(preference.isLocked()){
                lockPattern=new LockPattern(this);
            }
            setContentView(R.layout.activity_main);

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
