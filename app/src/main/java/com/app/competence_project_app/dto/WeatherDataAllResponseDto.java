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

    @SerializedName("data")
    List<Data> dataList;

    public WeatherDataAllResponseDto(RequestDateTime requestDateTime, Sensor sensor, List<Data> dataList) {
        this.requestDateTime = requestDateTime;
        this.sensor = sensor;
        this.dataList = dataList;
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

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }
}
