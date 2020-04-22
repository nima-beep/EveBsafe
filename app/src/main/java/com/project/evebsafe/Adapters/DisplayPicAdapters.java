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

public class DisplayPicAdapters extends ArrayAdapter<String> {

    int array[]={R.drawable.nairobi,R.drawable.professor1, R.drawable.sergio,R.drawable.tokyo};

    public DisplayPicAdapters(@NonNull Context context, int resource, @NonNull String[] objects) {
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

        if(textView.getText().toString().equals("Choose Picture"))
        {
            textView.setTextColor(Color.GRAY);

        }


        else
        {
            textView.setBackgroundResource(array[position-1]);


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

            textView.setBackgroundResource(array[position-1]);
        }


        return view;
    }
}
