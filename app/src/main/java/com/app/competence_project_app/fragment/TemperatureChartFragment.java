package com.app.competence_project_app.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.competence_project_app.R;
import com.app.competence_project_app.api.WeatherDataApiCallback;
import com.app.competence_project_app.api.WeatherDataApiImpl;
import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.model.Data;
import com.app.competence_project_app.util.formatter.PointLabelValueFormatter;
import com.app.competence_project_app.util.formatter.XLabelValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureChartFragment extends Fragment {

    private int pageNumber;

    private LineChart lineChart;

    private MaterialTextView chartTitle;

    private List<Entry> entries;

    private String unit;

    public TemperatureChartFragment() {
    }

    public static TemperatureChartFragment newInstance(Integer pageNumber) {
        TemperatureChartFragment fragment = new TemperatureChartFragment();
        Bundle args = new Bundle();
        args.putInt("pageNumber", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt("pageNumber");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_chart, container, false);

        lineChart = view.findViewById(R.id.chart);
        chartTitle = view.findViewById(R.id.chart_title);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onWeatherDataResponse();
    }

    private void setData() {
        LineDataSet set = new LineDataSet(entries, chartTitle.getText().toString());
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(Color.GREEN);
        set.setLineWidth(2f);
        set.setCircleColor(Color.GREEN);
        set.setCircleRadius(5f);
        set.setFillAlpha(65);
        set.setFillColor(Color.GREEN);
        set.setHighLightColor(Color.GREEN);
        set.setValueTextSize(14f);
        set.setValueTextColor(Color.WHITE);
        set.setDrawValues(true);
        set.setValueFormatter(new PointLabelValueFormatter(unit));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1000 * 60 * 60);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new XLabelValueFormatter());
        xAxis.setGridColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setGridColor(Color.WHITE);
        leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);

        lineChart.refreshDrawableState();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onWeatherDataResponse() {
        LocalDateTime from = LocalDateTime.parse("2023-04-11T12:43:21");
        LocalDateTime to = LocalDateTime.parse("2023-04-15T12:43:21");
        long sensorId = 1;

        WeatherDataApiImpl.getInstance().getAllWeatherData(sensorId, from, to, new WeatherDataApiCallback<WeatherDataAllResponseDto>() {
            @Override
            public void onSuccess(WeatherDataAllResponseDto body) {
                Data data = body.getDataList().get(pageNumber);
                unit = data.getUnit();
                String name = data.getName().toLowerCase();
                chartTitle.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
                entries = data.getMeasurementList()
                        .stream()
                        .map(measurement -> new Entry(getTimeInMillis(measurement.getDatetime()), measurement.getResult()))
                        .collect(Collectors.toList());

                lineChart.getDescription().setEnabled(false);
                lineChart.getLegend().setTextColor(Color.WHITE);
                lineChart.setVisibleXRangeMaximum(entries.size());
                lineChart.getLegend().setEnabled(false);

                setData();

                Log.i("WEATHER DATA ALL: ", body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i("WEATHER DATA ALL NOT FOUND: ", e.getMessage());
            }
        });
    }

    private long getTimeInMillis(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(timestamp);
            if (date == null) {
                return 0;
            }

            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

