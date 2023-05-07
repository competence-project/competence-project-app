package com.app.competence_project_app.model;

public class Measurement {

    private String datetime;

    private float result;

    public Measurement(String datetime, float result) {
        this.datetime = datetime;
        this.result = result;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }
}
