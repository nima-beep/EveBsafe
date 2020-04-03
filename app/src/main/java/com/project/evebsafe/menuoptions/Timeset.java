package com.project.evebsafe.menuoptions;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Linkers.Backtrack;
import com.project.evebsafe.MainActivity;
import com.project.evebsafe.R;

public class Timeset extends Fragment implements View.OnClickListener, NumberPicker.OnValueChangeListener {
  View view;
  SharedPreference preference;
  NumberPicker hour,minute,second;
  Button save,cancel;
  TextView display;
  Backtrack backtrack;
  Context  context;
 int H,M,S;
    public Timeset(Context context, Backtrack backtrack) {
        this.context=context;
        this.backtrack=backtrack;
        preference=new SharedPreference(context);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.timeset,container,false);
        hour=view.findViewById(R.id.hour);
        minute=view.findViewById(R.id.minutes);
        second=view.findViewById(R.id.seconds);
        save=view.findViewById(R.id.timesetSave);
        cancel=view.findViewById(R.id.timesetCancel);
        display=view.findViewById(R.id.timeValue);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
        hour.setOnValueChangedListener(this);
        minute.setOnValueChangedListener(this);
        second.setOnValueChangedListener(this);
        hour.setMaxValue(12);
        hour.setMinValue(0);
        minute.setMaxValue(59);
        minute.setMinValue(0);
        second.setMaxValue(59);
        second.setMinValue(0);
        display.setText(String.valueOf(preference.getHour())+" : "+String.valueOf(preference.getMinute())+" : " +String.valueOf(preference.getSecond()));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.timesetSave:
                display.setText(String.valueOf(H)+" : "+String.valueOf(M)+" : " +String.valueOf(S));
                preference.saveTime(H,M,S);

                break;
            case R.id.timesetCancel:
                backtrack.onCancel();

                break;




        }

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        switch (picker.getId()){
            case R.id.hour:
                H=newVal;
                break;
            case R.id.minutes:
                M=newVal;
                break;
            case R.id.seconds:
                S=newVal;
                break;


        }
    }
}
