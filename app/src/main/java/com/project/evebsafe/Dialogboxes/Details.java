package com.project.evebsafe.Dialogboxes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.evebsafe.Database.UserInfo;
import com.project.evebsafe.R;

import java.util.ArrayList;


public class Details extends Dialog {

    int width,height;

    TextView profilepic,gender,occupation,address,name,number;
    int array[]={R.drawable.frnd1,R.drawable.pro1, R.drawable.pro2,R.drawable.frnd2};

    public Details(@NonNull Context context,String Number,UserInfo userInfo) {
        super(context);
        ArrayList<String>data=userInfo.getIndividualInfo(Number);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);//to access the display information
        Display display=windowManager.getDefaultDisplay();//display information is stored in display object
        Point point=new Point();//for fixing in different phones
        display.getSize(point);//display is converted into points
        width=point.x;
        height=point.y;
        setContentView(R.layout.infolayout);
        getWindow().setLayout(width,(6*height)/7);
        show();
        profilepic=findViewById(R.id.PROFILE_ID);
       occupation=findViewById(R.id.OCCUPATION_ID);
       number=findViewById(R.id.NUMBER_ID);
       gender=findViewById(R.id.GENDER_ID);
       address=findViewById(R.id.ADDRESS_ID);
       name=findViewById(R.id.NAME_ID);

       name.setText(data.get(0));
       number.setText(data.get(1));
       address.setText(data.get(2));
       profilepic.setBackgroundResource(array[Integer.parseInt(data.get(3))]);//in database the pic is not saved,the index number is saved
       occupation.setText(data.get(4));
       gender.setText(data.get(5));


    }
}
