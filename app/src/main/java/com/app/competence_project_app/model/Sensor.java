package com.app.competence_project_app.model;

import com.google.gson.annotations.SerializedName;

public class Sensor {

    @SerializedName("mac_address")
    private String macAddress;

    public Sensor(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
