package com.app.competence_project_app.dto;

import com.app.competence_project_app.model.Measurement;
import com.app.competence_project_app.model.RequestDateTime;
import com.app.competence_project_app.model.Sensor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataResponseDto {

    @SerializedName("request_datetime")
    private RequestDateTime requestDateTime;

    private Sensor sensor;

    private String name;

    private String unit;

    private List<Measurement> measurementList;

    public WeatherDataResponseDto(RequestDateTime requestDateTime, Sensor sensor, String name, String unit, List<Measurement> measurementList) {
        this.requestDateTime = requestDateTime;
        this.sensor = sensor;
        this.name = name;
        this.unit = unit;
        this.measurementList = measurementList;
    }

    public RequestDateTime getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(RequestDateTime requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
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
