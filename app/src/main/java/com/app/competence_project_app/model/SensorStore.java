package com.app.competence_project_app.model;

public class SensorStore {

    private String macAddress;

    private String localization;

    public SensorStore(String macAddress, String localization) {
        this.macAddress = macAddress;
        this.localization = localization;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }
}
