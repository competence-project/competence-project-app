package com.app.competence_project_app.dto;

import com.app.competence_project_app.model.Data;
import com.app.competence_project_app.model.RequestDateTime;
import com.app.competence_project_app.model.Sensor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataAllResponseDto {

    @SerializedName("datetime")
    private RequestDateTime requestDateTime;

    private Sensor sensor;

    @SerializedName("temp")
    List<Data> temperatureDataList;

    @SerializedName("illum")
    List<Data> luminanceDataList;

    @SerializedName("pssr")
    List<Data> pressureDataList;

    @SerializedName("hum")
    List<Data> humidityDataList;

    public WeatherDataAllResponseDto(RequestDateTime requestDateTime, Sensor sensor, List<Data> temperatureDataList, List<Data> luminanceDataList, List<Data> pressureDataList, List<Data> humidityDataList) {
        this.requestDateTime = requestDateTime;
        this.sensor = sensor;
        this.temperatureDataList = temperatureDataList;
        this.luminanceDataList = luminanceDataList;
        this.pressureDataList = pressureDataList;
        this.humidityDataList = humidityDataList;
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

    public List<Data> getTemperatureDataList() {
        return temperatureDataList;
    }

    public void setTemperatureDataList(List<Data> temperatureDataList) {
        this.temperatureDataList = temperatureDataList;
    }

    public List<Data> getLuminanceDataList() {
        return luminanceDataList;
    }

    public void setLuminanceDataList(List<Data> luminanceDataList) {
        this.luminanceDataList = luminanceDataList;
    }

    public List<Data> getPressureDataList() {
        return pressureDataList;
    }

    public void setPressureDataList(List<Data> pressureDataList) {
        this.pressureDataList = pressureDataList;
    }

    public List<Data> getHumidityDataList() {
        return humidityDataList;
    }

    public void setHumidityDataList(List<Data> humidityDataList) {
        this.humidityDataList = humidityDataList;
    }
}
