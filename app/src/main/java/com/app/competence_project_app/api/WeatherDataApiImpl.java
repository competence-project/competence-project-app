package com.app.competence_project_app.api;

import androidx.annotation.NonNull;

import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.dto.WeatherDataResponseDto;

import java.time.LocalDateTime;

import co.infinum.retromock.Retromock;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataApiImpl {

    private static WeatherDataApiImpl weatherDataApiImpl = null;

    private static WeatherDataApi weatherDataApi;

    private WeatherDataApiImpl() {
        weatherDataApi = new Retromock.Builder()
                .retrofit(new retrofit2.Retrofit.Builder().baseUrl("http://localhost8080").addConverterFactory(GsonConverterFactory.create()).build())
                .build()
                .create(WeatherDataApiMock.class);
    }

    public static WeatherDataApiImpl getInstance() {
        if (weatherDataApiImpl == null) {
            weatherDataApiImpl = new WeatherDataApiImpl();
        }

        return weatherDataApiImpl;
    }

    public void getAllWeatherData(long sensorId, LocalDateTime from, LocalDateTime to, WeatherDataApiCallback<WeatherDataAllResponseDto> callback) {
        weatherDataApi.getAllWeatherData(sensorId, from, to).enqueue(new Callback<WeatherDataAllResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDataAllResponseDto> call, @NonNull Response<WeatherDataAllResponseDto> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception("WEATHER DATA ALL NOT FOUND"));
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherDataAllResponseDto> call, @NonNull Throwable t) {
                callback.onFailure(new Exception("RESPONSE FAILED"));
            }
        });
    }

    public void getTemperatureWeatherData(long sensorId, LocalDateTime from, LocalDateTime to, WeatherDataApiCallback<WeatherDataResponseDto> callback) {
        weatherDataApi.getTemperatureWeatherData(sensorId, from, to).enqueue(new Callback<WeatherDataResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDataResponseDto> call, @NonNull Response<WeatherDataResponseDto> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception("TEMPERATURE WEATHER DATA NOT FOUND"));
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherDataResponseDto> call, @NonNull Throwable t) {
                callback.onFailure(new Exception("RESPONSE FAILED"));
            }
        });
    }

    public void getHumidityWeatherData(long sensorId, LocalDateTime from, LocalDateTime to, WeatherDataApiCallback<WeatherDataResponseDto> callback) {
        weatherDataApi.getHumidityWeatherData(sensorId, from, to).enqueue(new Callback<WeatherDataResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDataResponseDto> call, @NonNull Response<WeatherDataResponseDto> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception("HUMIDITY WEATHER DATA NOT FOUND"));
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherDataResponseDto> call, @NonNull Throwable t) {
                callback.onFailure(new Exception("RESPONSE FAILED"));
            }
        });
    }

    public void getPressureWeatherData(long sensorId, LocalDateTime from, LocalDateTime to, WeatherDataApiCallback<WeatherDataResponseDto> callback) {
        weatherDataApi.getPressureWeatherData(sensorId, from, to).enqueue(new Callback<WeatherDataResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDataResponseDto> call, @NonNull Response<WeatherDataResponseDto> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception("PRESSURE WEATHER DATA NOT FOUND"));
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherDataResponseDto> call, @NonNull Throwable t) {
                callback.onFailure(new Exception("RESPONSE FAILED"));
            }
        });
    }

    public void getLuminanceWeatherData(long sensorId, LocalDateTime from, LocalDateTime to, WeatherDataApiCallback<WeatherDataResponseDto> callback) {
        weatherDataApi.getLuminanceWeatherData(sensorId, from, to).enqueue(new Callback<WeatherDataResponseDto>() {
            @Override
            public void onResponse(@NonNull Call<WeatherDataResponseDto> call, @NonNull Response<WeatherDataResponseDto> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception("LUMINANCE WEATHER DATA NOT FOUND"));
                    return;
                }

                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherDataResponseDto> call, @NonNull Throwable t) {
                callback.onFailure(new Exception("RESPONSE FAILED"));
            }
        });
    }
}
