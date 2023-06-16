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

import com.app.competence_project_app.R;
import com.app.competence_project_app.api.WeatherDataApiCallback;
import com.app.competence_project_app.api.WeatherDataApiImpl;
import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.model.Data;
import com.app.competence_project_app.util.constant.Constant;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureChartFragment extends Fragment {

    private long macAddress;

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
        args.putInt(Constant.PAGE_NUMBER, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt(Constant.PAGE_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_chart, container, false);

        lineChart = view.findViewById(R.id.chart);
        chartTitle = view.findViewById(R.id.chart_title);
        macAddress = 1;

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
        WeatherDataApiImpl.getInstance().getAllWeatherDataByMacAddress(macAddress, new WeatherDataApiCallback<WeatherDataAllResponseDto>() {
            @Override
            public void onSuccess(WeatherDataAllResponseDto body) {
                Data data = body.getDataList().get(pageNumber);
                unit = getUnitByDataName(body.getDataList().get(pageNumber).getName());
                chartTitle.setText(getTitleByDataName(body.getDataList().get(pageNumber).getName()));
                entries = data.getMeasurementList()
                        .stream()
                        .map(measurement -> new Entry(measurement.getDatetime() * 1000, Float.parseFloat(measurement.getResult())))
                        .collect(Collectors.toList());

                lineChart.getDescription().setEnabled(false);
                lineChart.getLegend().setTextColor(Color.WHITE);
                lineChart.setVisibleXRangeMaximum(entries.size());
                lineChart.getLegend().setEnabled(false);

                setData();

                Log.i(Constant.SUCCESS_WEATHER_DATA_ALL, body.toString());
            }

            @Override
            public void onFailure(Exception e) {
                Log.i(Constant.FAILURE_WEATHER_DATA_ALL, e.getMessage());
            }
        });
    }

    private String getUnitByDataName(String name) {
        if (Constant.TEMP.equals(name)) {
            return Constant.TEMP_UNIT;
        } else if (Constant.HUM.equals(name)) {
            return Constant.HUM_UNIT;
        } else if (Constant.PSSR.equals(name)) {
            return Constant.PSSR_UNIT;
        } else if (Constant.ILLUM.equals(name)) {
            return Constant.ILLUM_UNIT;
        } else {
            return Constant.BLANK;
        }
    }

    private String getTitleByDataName(String name) {
        if (Constant.TEMP.equals(name)) {
            return Constant.TEMP_LABEL;
        } else if (Constant.HUM.equals(name)) {
            return Constant.HUM_LABEL;
        } else if (Constant.PSSR.equals(name)) {
            return Constant.PSSR_LABEL;
        } else if (Constant.ILLUM.equals(name)) {
            return Constant.ILLUM_LABEL;
        } else {
            return Constant.BLANK;
        }
    }
}

