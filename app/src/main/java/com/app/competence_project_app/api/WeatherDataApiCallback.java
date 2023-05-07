package com.app.competence_project_app.api;

public interface WeatherDataApiCallback<T> {
    void onSuccess(T body);

    void onFailure(Exception e);
}
