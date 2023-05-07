package com.app.competence_project_app.util.formatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class PointLabelValueFormatter extends ValueFormatter {

    private final String unit;

    public PointLabelValueFormatter(String unit) {
        this.unit = unit;
    }

    @Override
    public String getPointLabel(Entry entry) {
        return entry.getY() + " " + unit;
    }
}
