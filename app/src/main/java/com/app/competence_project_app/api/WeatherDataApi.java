package com.app.competence_project_app.api;

import com.app.competence_project_app.dto.WeatherDataAllResponseDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WeatherDataApi {

    @GET("/data/{mac_addr}")
    Call<List<WeatherDataAllResponseDto>> getAllWeatherDataByMacAddress(@Path("mac_addr") long macAddress);
}
