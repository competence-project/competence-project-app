package com.app.competence_project_app.api;

import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.dto.WeatherDataResponseDto;

import java.time.LocalDateTime;

import co.infinum.retromock.meta.Mock;
import co.infinum.retromock.meta.MockResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherDataApiMock extends WeatherDataApi {

    @Mock()
    @MockResponse(body = "{\"request_datetime\":{\"from\":\"2023-04-11T12:23:21\",\"to\":\"2023-04-15T12:23:21\",\"timezone_offset\":\"7200\"},\"sensor\":{\"mac_address\":\"30AEA4070D64\",\"localization\":\"Main hall\"},\"data\":[{\"name\":\"temperature\",\"unit\":\"°C\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":14.45},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":15.3},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":12.3},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":18.0},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":12.3}]},{\"name\":\"luminance\",\"unit\":\"cd/m²\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":1.56},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":1.8},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":2.21},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":1.92},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":2.47}]},{\"name\":\"humidity\",\"unit\":\"%\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":43},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":56},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":41},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":62},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":32}]},{\"name\":\"pressure\",\"unit\":\"hPa\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":1012},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":1011},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":1023},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":1014},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":1017}]}]}")
    @GET("/weather/data/all?")
    Call<WeatherDataAllResponseDto> getAllWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") LocalDateTime from,
            @Query("to") LocalDateTime to
    );

    @Mock()
    @MockResponse(body = "{\"request_datetime\":{\"from\":\"2023-04-11T12:23:21\",\"to\":\"2023-04-15T12:23:21\",\"timezone_offset\":\"7200\"},\"sensor\":{\"mac_address\":\"30AEA4070D64\",\"localization\":\"Mainhall\"},\"name\":\"temperature\",\"unit\":\"°C\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":14.45},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":15.3},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":12.3},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":18.0},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":12.3}]}")
    @GET("/weather/data/temperature?")
    Call<WeatherDataResponseDto> getTemperatureWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") LocalDateTime from,
            @Query("to") LocalDateTime to
    );

    @Mock()
    @MockResponse(body = "{\"request_datetime\":{\"from\":\"2023-04-11T12:23:21\",\"to\":\"2023-04-15T12:23:21\",\"timezone_offset\":\"7200\"},\"sensor\":{\"mac_address\":\"30AEA4070D64\",\"localization\":\"Mainhall\"},\"name\":\"humidity\",\"unit\":\"%\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":43},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":56},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":41},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":62},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":32}]}")
    @GET("/weather/data/humidity?")
    Call<WeatherDataResponseDto> getHumidityWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") LocalDateTime from,
            @Query("to") LocalDateTime to
    );

    @Mock()
    @MockResponse(body = "{\"request_datetime\":{\"from\":\"2023-04-11T12:23:21\",\"to\":\"2023-04-15T12:23:21\",\"timezone_offset\":\"7200\"},\"sensor\":{\"mac_address\":\"30AEA4070D64\",\"localization\":\"Mainhall\"},\"name\":\"pressure\",\"unit\":\"hPa\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":1012},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":1011},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":1023},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":1014},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":1017}]}")
    @GET("/weather/data/pressure?")
    Call<WeatherDataResponseDto> getPressureWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") LocalDateTime from,
            @Query("to") LocalDateTime to
    );

    @Mock()
    @MockResponse(body = "{\"request_datetime\":{\"from\":\"2023-04-11T12:23:21\",\"to\":\"2023-04-15T12:23:21\",\"timezone_offset\":\"7200\"},\"sensor\":{\"mac_address\":\"30AEA4070D64\",\"localization\":\"Mainhall\"},\"name\":\"luminance\",\"unit\":\"cd/m²\",\"measurement_list\":[{\"datetime\":\"2023-04-11T12:43:21\",\"result\":1.56},{\"datetime\":\"2023-04-12T15:53:29\",\"result\":1.8},{\"datetime\":\"2023-04-13T16:13:05\",\"result\":2.21},{\"datetime\":\"2023-04-14T09:54:14\",\"result\":1.92},{\"datetime\":\"2023-04-15T10:13:21\",\"result\":2.47}]}")
    @GET("/weather/data/luminance?")
    Call<WeatherDataResponseDto> getLuminanceWeatherData(
            @Query("sensor") long sensorId,
            @Query("from") LocalDateTime from,
            @Query("to") LocalDateTime to
    );
}
