package com.project.evebsafe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.project.evebsafe.Model.LocationResponse;
import com.project.evebsafe.R;


import java.util.List;

public class LocationListViewAdapter extends BaseAdapter {

    Context context;
    View view;
    List<LocationResponse> locationResponse;
    TextView street, city, country, zip, time;
    int size;
    public LocationListViewAdapter(Context context, List<LocationResponse> locationResponse) {
        this.context = context;
        this.locationResponse = locationResponse;
        size=locationResponse.size()-1;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.singleshowlocation, parent, false);
        street = view.findViewById(R.id.streetId);
        city = view.findViewById(R.id.cityId);
        zip = view.findViewById(R.id.zipId);
        country = view.findViewById(R.id.countryId);
        time = view.findViewById(R.id.timeId);

        street.setText("Street:  "+locationResponse.get(size-position).getStreet());
        city.setText("City:  "+locationResponse.get(size-position).getCity());
        zip.setText("Zip:  "+locationResponse.get(size-position).getZip());
        country.setText("Country:  "+locationResponse.get(size-position).getCountry());
        time.setText("Time:  "+locationResponse.get(size-position).getCurrentTime());


        return view;
    }

    @Override
    public int getCount() {

        return locationResponse.size();
    }

    @Override
    public Object getItem(int position) {
        return locationResponse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
