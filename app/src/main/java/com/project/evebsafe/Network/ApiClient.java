package com.project.evebsafe.Network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static Retrofit retrofit=null;
    public static String Base_Url="https://womensafetyappevebsafe.000webhostapp.com/data/";

    public static Retrofit getClient(){

        if(retrofit==null){

            //retrofit calling API
            retrofit=new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //
        }

        return retrofit;
    }
}