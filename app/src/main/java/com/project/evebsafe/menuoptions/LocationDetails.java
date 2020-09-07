package com.project.evebsafe.menuoptions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.project.evebsafe.Adapters.LocationListViewAdapter;
import com.project.evebsafe.Database.SharedPreference;
import com.project.evebsafe.Model.LocationResponse;
import com.project.evebsafe.Model.RegistrationState;
import com.project.evebsafe.Network.ApiClient;
import com.project.evebsafe.Network.ApiService;
import com.project.evebsafe.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDetails extends Fragment {
    View view;
    ListView listView;
    LocationListViewAdapter locationListViewAdapter;
    SharedPreference preference;
    public LocationDetails(SharedPreference preference) {
        this.preference=preference;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.showlocation,container,false);
        listView=view.findViewById(R.id.showlocatinlistview);//
        ApiService apiService= ApiClient.getClient().create(ApiService.class);//network calling for fetching data from server
     Call<List<LocationResponse>>call=apiService.locationget(preference.getPhone(),preference.getEmail());
     call.enqueue(new Callback<List<LocationResponse>>() {
         @Override
         public void onResponse(Call<List<LocationResponse>> call, Response<List<LocationResponse>> response) {
            locationListViewAdapter=new LocationListViewAdapter(getContext(),response.body());
            listView.setAdapter(locationListViewAdapter);
         }

         @Override
         public void onFailure(Call<List<LocationResponse>> call, Throwable t) {
             Toast.makeText(getContext(), "Failed to fetch location", Toast.LENGTH_SHORT).show();
         }
     });
        return view;
    }
}
