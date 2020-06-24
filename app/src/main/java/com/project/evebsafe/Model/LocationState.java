package com.project.evebsafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationState {

    @Expose
    @SerializedName("result")
    boolean result;


    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }



}
