package com.app.competence_project_app.model;

import com.google.gson.annotations.SerializedName;

public class DateTime {

    private String from;

    private String to;

    @SerializedName("timezone_offset")
    private String timezoneOffset;
}
