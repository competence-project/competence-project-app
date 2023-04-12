package com.app.competence_project_app.model;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class TemperatureBarEntry {

    ArrayList<BarEntry> temperatureMin = new ArrayList<>();

    ArrayList<BarEntry> temperatureMax = new ArrayList<>();

    public TemperatureBarEntry() {
        temperatureMin.add(new BarEntry(0L, 21));
        temperatureMin.add(new BarEntry(2L, 17));
        temperatureMin.add(new BarEntry(4L, 24));
        temperatureMin.add(new BarEntry(6L, 20));
        temperatureMin.add(new BarEntry(8L, 19));

        temperatureMax.add(new BarEntry(0L, 23));
        temperatureMax.add(new BarEntry(2L, 18));
        temperatureMax.add(new BarEntry(4L, 24));
        temperatureMax.add(new BarEntry(6L, 21));
        temperatureMax.add(new BarEntry(8L, 22));
    }

    public ArrayList<BarEntry> getTemperatureMin() { return temperatureMin; }

    public ArrayList<BarEntry> getTemperatureMax() { return temperatureMax; }
}
