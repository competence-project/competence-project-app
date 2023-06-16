package com.app.competence_project_app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    private long id;

    @SerializedName("measurement_list")
    private List<Measurement> measurementList;

    public Data(long id, List<Measurement> measurementList) {
        this.id = id;
        this.measurementList = measurementList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }
}
