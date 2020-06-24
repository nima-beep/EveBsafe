package com.project.evebsafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationResponse {

    @Expose
    @SerializedName("City")
    private String City;


    @Expose
    @SerializedName("Street")
    private String Street;

    @Expose
    @SerializedName("Country")
    private String Country;


    @Expose
    @SerializedName("Zip")
    private String Zip;


    @Expose
    @SerializedName("CurrentTime")
    private String CurrentTime;


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }



    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String zip) {
        Zip = zip;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }




}
