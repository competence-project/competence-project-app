package com.app.competence_project_app.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RealTimeViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<Payload> payload = new MutableLiveData<>();

    public static class Payload {
        public String data;
        public String topic;

        public Payload(String data, String topic) {
            this.data = data;
            this.topic = topic;
        }
    }

    public void setPayload(Payload data) {
        this.payload.setValue(data);
    }

    public LiveData<Payload> getPayload(){
        return this.payload;
    }

}