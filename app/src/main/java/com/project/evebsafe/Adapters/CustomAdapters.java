package com.project.evebsafe.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.project.evebsafe.R;

public class CustomAdapters extends ArrayAdapter<String> {


    public CustomAdapters(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);

    }

    @Override
    public boolean isEnabled(int position) {
       if(position==0)
       {
           return false;

       }
       else
       {
           return true;
       }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        View view=super.getDropDownView(position, convertView, parent);
        textView=(TextView)view;

        if(textView.getText().toString().equals("Choose Occupation"))
        {
            textView.setTextColor(Color.GRAY);

        }
        else if(textView.getText().toString().equals("Choose Gender"))
        {
            textView.setTextColor(Color.GRAY);

        }


        else
        {
            textView.setTextColor(Color.BLACK);


        }


        return view;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        View view=super.getDropDownView(position, convertView, parent);
        textView=(TextView)view;
           if(position==0)
           {
               textView.setTextColor(Color.GRAY);
           }
           else
           {

               textView.setTextColor(Color.BLACK);
           }


        return view;
    }
}
