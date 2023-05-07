package com.app.competence_project_app.util.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XLabelValueFormatter extends ValueFormatter {

    private final DateFormat mDateFormat;

    public XLabelValueFormatter() {
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        Date date = new Date((long) value);
        return mDateFormat.format(date);
    }
}
