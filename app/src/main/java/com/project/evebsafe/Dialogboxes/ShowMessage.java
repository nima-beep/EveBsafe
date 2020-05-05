package com.project.evebsafe.Dialogboxes;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.R;


public class ShowMessage extends Dialog {
    TextView msg,hr,min,sec;
    SharedPreference sharedPreference;

    public ShowMessage(@NonNull Context context,SharedPreference sharedPreference) {
        super(context);
        this.sharedPreference=sharedPreference;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.messageandtime);
     //   getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
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
