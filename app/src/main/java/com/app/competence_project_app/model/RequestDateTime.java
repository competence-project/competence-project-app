package com.app.competence_project_app.model;

import com.google.gson.annotations.SerializedName;

public class RequestDateTime {

    private String from;

    private String to;

    @SerializedName("timezone_offset")
    private String timezoneOffset;

    public RequestDateTime(String from, String to, String timezoneOffset) {
        this.from = from;
        this.to = to;
        this.timezoneOffset = timezoneOffset;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
}
