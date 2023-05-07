package com.app.competence_project_app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    private String name;

    private String unit;

    @SerializedName("measurement_list")
    private List<Measurement> measurementList;

    public Data(String name, String unit, List<Measurement> measurementList) {
        this.name = name;
        this.unit = unit;
        this.measurementList = measurementList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }
}
