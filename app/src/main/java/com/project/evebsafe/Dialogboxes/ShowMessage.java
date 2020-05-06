package com.project.evebsafe.Dialogboxes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.R;


public class ShowMessage extends Dialog {
    TextView msg,hr,min,sec;
    SharedPreference sharedPreference;
    int width,height;

    public ShowMessage(@NonNull Context context,SharedPreference sharedPreference) {
        super(context);
        this.sharedPreference=sharedPreference;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);//to access the display information
        Display display=windowManager.getDefaultDisplay();//display information is stored in display object
        Point point=new Point();
        display.getSize(point);//display is converted into points
        width=point.x;
        height=point.y;
        setContentView(R.layout.messageandtime);
       getWindow().setLayout(width,(6*height)/7);
        msg=findViewById(R.id.showMessageId);
        hr=findViewById(R.id.showhr);
        min=findViewById(R.id.showmin);
        sec=findViewById(R.id.showsec);
        msg.setText(sharedPreference.getMessage());
        msg.setMovementMethod(new ScrollingMovementMethod());
        hr.setText(String.valueOf(sharedPreference.getHour())+" hr");
        min.setText(String.valueOf(sharedPreference.getMinute())+" min");
        sec.setText(String.valueOf(sharedPreference.getSecond())+" sec");
        show();


    }
}
