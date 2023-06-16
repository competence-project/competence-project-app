package com.app.competence_project_app.api;

import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.dto.WeatherDataResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataApi {

    @GET("/weather/data/all?")
    Call<WeatherDataAllResponseDto> getAllWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") long from,
            @Query("to") long to
    );

    @GET("/weather/data/temperature?")
    Call<WeatherDataResponseDto> getTemperatureWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") long from,
            @Query("to") long to
    );

    @GET("/weather/data/humidity?")
    Call<WeatherDataResponseDto> getHumidityWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") long from,
            @Query("to") long to
    );

    @GET("/weather/data/pressure?")
    Call<WeatherDataResponseDto> getPressureWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") long from,
            @Query("to") long to
    );

    @GET("/weather/data/luminance?")
    Call<WeatherDataResponseDto> getLuminanceWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") long from,
            @Query("to") long to
    );
}
