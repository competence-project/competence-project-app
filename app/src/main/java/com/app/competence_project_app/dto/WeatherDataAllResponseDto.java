package com.app.competence_project_app.dto;

import com.app.competence_project_app.model.Data;
import com.app.competence_project_app.model.RequestDateTime;
import com.app.competence_project_app.model.Sensor;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherDataAllResponseDto {

    @SerializedName("request_datetime")
    private RequestDateTime requestDateTime;

    private Sensor sensor;

    @SerializedName("data")
    List<Data> dataList;
}
