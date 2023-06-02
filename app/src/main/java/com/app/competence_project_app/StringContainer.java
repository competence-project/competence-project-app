package com.app.competence_project_app;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class StringContainer extends Application {
    private String topic = "?";
    private String data = "?";
    private final List<Observer> observers = new ArrayList<>();

    public String getTopic() {
        return topic;
    }

    public void setTopicData(String topic, String data) {
        this.topic = topic;
        this.data = data;
        notifyObservers();
    }

    public String getData() {
        return data;
    }

    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.onStringUpdated(topic, data);
        }
    }
}