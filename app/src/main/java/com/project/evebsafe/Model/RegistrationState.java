package com.project.evebsafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
//for data receive
public class RegistrationState {
    @Expose
    @SerializedName("StatusCode")
    private int StatusCode;

    @Expose
    @SerializedName("Name")
    private String Name;

    @Expose
    @SerializedName("PhoneNumber")
    private String PhoneNumber;

    @Expose
    @SerializedName("Address")
    private String Address;

    @Expose
    @SerializedName("Email")
    private String Email;


    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
