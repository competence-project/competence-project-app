package com.app.competence_project_app.model;

public class Measurement {

    private long datetime;

    private String result;

    public Measurement(long datetime, String result) {
        this.datetime = datetime;
        this.result = result;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
