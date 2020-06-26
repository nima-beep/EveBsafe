package com.project.evebsafe.Network;

import com.project.evebsafe.Model.LocationResponse;
import com.project.evebsafe.Model.LocationState;
import com.project.evebsafe.Model.RegistrationState;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
//for sending data
public interface ApiService {

    // for using post method we used @FormUrlEncoded
    @FormUrlEncoded
    @POST("registration.php")
    Call<RegistrationState> registration(@Field("Name") String Name, @Field("PhoneNumber") String PhoneNumber, @Field("Address") String Address, @Field("Email") String Email);


    @FormUrlEncoded
    @POST("registeredalready.php")
    Call<RegistrationState> registeredalready( @Field("PhoneNumber") String PhoneNumber, @Field("Email") String Email);


    @FormUrlEncoded
    @POST("locationinsert.php")
    Call<LocationState> locationinsert( @Field("PhoneNumber") String PhoneNumber,@Field("Email") String Email,@Field("City") String City,@Field("Street") String Street,@Field("Country") String Country,@Field("Zip") String Zip,@Field("Time") String Time);



    @FormUrlEncoded
    @POST("locationget.php")
    Call<List<LocationResponse>> locationget(@Field("PhoneNumber") String PhoneNumber, @Field("Email") String Email);
}