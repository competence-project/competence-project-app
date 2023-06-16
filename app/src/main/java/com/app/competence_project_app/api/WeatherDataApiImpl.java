package com.app.competence_project_app.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.util.constant.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherDataApiImpl {

    private static WeatherDataApiImpl weatherDataApiImpl = null;

    private static WeatherDataApi weatherDataApi;

    private WeatherDataApiImpl() {
        weatherDataApi = new retrofit2.Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherDataApi.class);
    }

    public static WeatherDataApiImpl getInstance() {
        if (weatherDataApiImpl == null) {
            weatherDataApiImpl = new WeatherDataApiImpl();
        }

        return weatherDataApiImpl;
    }

    public void getAllWeatherDataByMacAddress(String macAddress, WeatherDataApiCallback<WeatherDataAllResponseDto> callback) {
        weatherDataApi.getAllWeatherDataByMacAddress(macAddress).enqueue(new Callback<List<WeatherDataAllResponseDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<WeatherDataAllResponseDto>> call, @NonNull Response<List<WeatherDataAllResponseDto>> response) {
                if (response.body() == null) {
                    callback.onFailure(new Exception(Constant.FAILURE_WEATHER_DATA_ALL));
                    return;
                }

                Log.i(Constant.RESPONSE_SUCCESS, response.body().toString());
                callback.onSuccess(response.body().get(0));
            }

            @Override
            public void onFailure(@NonNull Call<List<WeatherDataAllResponseDto>> call, @NonNull Throwable t) {
                callback.onFailure(new Exception(Constant.RESPONSE_FAILED));
            }
        });
    }
}
