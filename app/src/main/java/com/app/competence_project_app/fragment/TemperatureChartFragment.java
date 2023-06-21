package com.app.competence_project_app.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.app.competence_project_app.R;
import com.app.competence_project_app.api.WeatherDataApiCallback;
import com.app.competence_project_app.api.WeatherDataApiImpl;
import com.app.competence_project_app.dto.WeatherDataAllResponseDto;
import com.app.competence_project_app.model.Measurement;
import com.app.competence_project_app.util.constant.Constant;
import com.app.competence_project_app.util.formatter.XLabelValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperatureChartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperatureChartFragment extends Fragment {

    private WeatherDataAllResponseDto weatherDataAllResponseDto;

    private String macAddress;

    private int pageNumber;

    private String unit;

    private String filter;

    private List<Entry> entries;

    private LineChart lineChart;

    private TextView chartTitleTextView;

    private TextView selectedEntryResultTextView;

    private TextView selectedEntryDatetimeTextView;

    private Button btnWeekly;

    private Button btnMonthly;

    private Button btnYearly;

    public TemperatureChartFragment() {
    }

    public static TemperatureChartFragment newInstance(Integer pageNumber, String macAddress) {
        TemperatureChartFragment fragment = new TemperatureChartFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.PAGE_NUMBER, pageNumber);
        args.putString(Constant.MAC_ADDRESS, macAddress);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageNumber = getArguments().getInt(Constant.PAGE_NUMBER);
            macAddress = getArguments().getString(Constant.MAC_ADDRESS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temperature_chart, container, false);

        lineChart = view.findViewById(R.id.chart);
        chartTitleTextView = view.findViewById(R.id.chart_title);
        selectedEntryDatetimeTextView = view.findViewById(R.id.selected_entry_datetime);
        selectedEntryResultTextView = view.findViewById(R.id.selected_entry_result);
        btnWeekly = view.findViewById(R.id.btn_weekly);
        btnMonthly = view.findViewById(R.id.btn_monthly);
        btnYearly = view.findViewById(R.id.btn_yearly);

        onButtonWeeklyInit();
        onButtonMonthlyInit();
        onButtonYearlyInit();

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onWeatherDataResponse();
    }

    private void setData() {
        LineDataSet set = new LineDataSet(entries, chartTitleTextView.getText().toString());
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
        set.setDrawValues(false);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1000 * 60 * 60);
        xAxis.setDrawGridLines(false);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new XLabelValueFormatter());
        xAxis.setLabelCount(3, true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setGridColor(Color.WHITE);
        leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setLabelCount(5, true);

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                selectedEntryDatetimeTextView.setText(getDateAsStringFromUnixTimestamp((long) entry.getX()));
                DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                selectedEntryResultTextView.setText(decimalFormat.format(entry.getY()) + " " + unit);
            }

            @Override
            public void onNothingSelected() {
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            private String getDateAsStringFromUnixTimestamp(long unixTimestamp) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getDateTimeFormatterByFilter());
                LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp / 1000), ZoneId.systemDefault());

                return localDateTime.format(formatter);
            }
        });

        lineChart.refreshDrawableState();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onWeatherDataResponse() {
        WeatherDataApiImpl.getInstance().getAllWeatherDataByMacAddress(macAddress, new WeatherDataApiCallback<WeatherDataAllResponseDto>() {
            @Override
            public void onSuccess(WeatherDataAllResponseDto body) {
                weatherDataAllResponseDto = body;
                filter = Constant.CLEAR;
                chartTitleTextView.setText(getDataListNameByPageNumber());
                unit = getDataListUnitByPageNumber();
                entries = toEntryListMapper(getMeasurementListByPageNumber(body));

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

    private String getDateTimeFormatterByFilter() {
        if (isYearlyFilter()) {
            return "yyyy-MM";
        } else if (isMonthlyFilter() | isWeeklyFilter()) {
            return "yyyy-MM-dd";
        } else {
            return "yyyy-MM-dd HH:mm";
        }
    }

    private String getDataListUnitByPageNumber() {
        if (pageNumber == Constant.TEMPERATURE_PAGE_NUMBER) {
            return Constant.TEMPERATURE_UNIT;
        } else if (pageNumber == Constant.LUMINANCE_PAGE_NUMBER) {
            return Constant.LUMINANCE_UNIT;
        } else if (pageNumber == Constant.PRESSURE_PAGE_NUMBER) {
            return Constant.PRESSURE_UNIT;
        } else {
            return Constant.HUMIDITY_UNIT;
        }
    }

    private String getDataListNameByPageNumber() {
        if (pageNumber == Constant.TEMPERATURE_PAGE_NUMBER) {
            return Constant.TEMPERATURE_LABEL;
        } else if (pageNumber == Constant.LUMINANCE_PAGE_NUMBER) {
            return Constant.LUMINANCE_LABEL;
        } else if (pageNumber == Constant.PRESSURE_PAGE_NUMBER) {
            return Constant.PRESSURE_LABEL;
        } else {
            return Constant.HUMIDITY_LABEL;
        }
    }

    private List<Measurement> getMeasurementListByPageNumber(WeatherDataAllResponseDto weatherDataAllResponseDto) {
        List<Measurement> measurementList = new ArrayList<>();
        if (pageNumber == Constant.TEMPERATURE_PAGE_NUMBER) {
            weatherDataAllResponseDto.getTemperatureDataList().forEach(data -> measurementList.addAll(data.getMeasurementList()));
        } else if (pageNumber == Constant.LUMINANCE_PAGE_NUMBER) {
            weatherDataAllResponseDto.getLuminanceDataList().forEach(data -> measurementList.addAll(data.getMeasurementList()));
        } else if (pageNumber == Constant.PRESSURE_PAGE_NUMBER) {
            weatherDataAllResponseDto.getPressureDataList().forEach(data -> measurementList.addAll(data.getMeasurementList()));
        } else {
            weatherDataAllResponseDto.getHumidityDataList().forEach(data -> measurementList.addAll(data.getMeasurementList()));
        }
        measurementList.sort(Comparator.comparingLong(Measurement::getDatetime));

        return measurementList;
    }

    private List<Entry> toEntryListMapper(List<Measurement> measurementList) {
        return measurementList
                .stream()
                .map(measurement -> new Entry(measurement.getDatetime() * 1000, Float.parseFloat(measurement.getResult().isEmpty() ? "0" : measurement.getResult())))
                .collect(Collectors.toList());
    }

    private void onButtonWeeklyInit() {
        btnWeekly.setOnClickListener(onClick -> {
            selectedEntryResultTextView.setText("-");
            selectedEntryDatetimeTextView.setText("-");
            btnMonthly.setText(Constant.MONTHLY);
            btnYearly.setText(Constant.YEARLY);

            if (Constant.WEEKLY.equals(btnWeekly.getText().toString())) {
                btnWeekly.setText(Constant.CLEAR);
                List<Measurement> measurementList = filterMeasurementListByNumberOfDays(getMeasurementListByPageNumber(weatherDataAllResponseDto), 7);
                entries = toEntryListMapper(measurementList);
                filter = Constant.WEEKLY;
            } else {
                btnWeekly.setText(Constant.WEEKLY);
                List<Measurement> measurementList = getMeasurementListByPageNumber(weatherDataAllResponseDto);
                entries = toEntryListMapper(measurementList);
                filter = Constant.CLEAR;
            }

            setData();
        });
    }

    private void onButtonMonthlyInit() {
        btnMonthly.setOnClickListener(onClick -> {
            selectedEntryResultTextView.setText("-");
            selectedEntryDatetimeTextView.setText("-");
            btnWeekly.setText(Constant.WEEKLY);
            btnYearly.setText(Constant.YEARLY);

            if (Constant.MONTHLY.equals(btnMonthly.getText().toString())) {
                btnMonthly.setText(Constant.CLEAR);
                List<Measurement> measurementList = filterMeasurementListByNumberOfDays(getMeasurementListByPageNumber(weatherDataAllResponseDto), 30);
                entries = toEntryListMapper(measurementList);
                filter = Constant.MONTHLY;
            } else {
                btnMonthly.setText(Constant.MONTHLY);
                List<Measurement> measurementList = getMeasurementListByPageNumber(weatherDataAllResponseDto);
                entries = toEntryListMapper(measurementList);
                filter = Constant.CLEAR;
            }

            setData();
        });
    }

    private void onButtonYearlyInit() {
        btnYearly.setOnClickListener(onClick -> {
            selectedEntryResultTextView.setText("-");
            selectedEntryDatetimeTextView.setText("-");
            btnWeekly.setText(Constant.WEEKLY);
            btnMonthly.setText(Constant.MONTHLY);

            if (Constant.YEARLY.equals(btnYearly.getText().toString())) {
                btnYearly.setText(Constant.CLEAR);
                List<Measurement> measurementList = filterMeasurementListByNumberOfMonths(getMeasurementListByPageNumber(weatherDataAllResponseDto), 12);
                entries = toEntryListMapper(measurementList);
                filter = Constant.YEARLY;
            } else {
                btnYearly.setText(Constant.YEARLY);
                List<Measurement> measurementList = getMeasurementListByPageNumber(weatherDataAllResponseDto);
                entries = toEntryListMapper(measurementList);
                filter = Constant.CLEAR;
            }

            setData();
        });
    }

    private double getAverageResultOfMeasurementList(List<Measurement> measurementList, long startInMillis, long endInMillis) {
        double sum = 0.0;
        int count = 0;

        for (Measurement measurement : measurementList) {
            long measurementTimeMillis = measurement.getDatetime() * 1000;
            if (measurementTimeMillis >= startInMillis && measurementTimeMillis < endInMillis) {
                sum += Double.parseDouble(measurement.getResult().isEmpty() ? "0" : measurement.getResult());
                count++;
            }
        }

        return count > 0 ? sum / count : 0.0;
    }

    private List<Measurement> filterMeasurementListByNumberOfDays(List<Measurement> measurementList, long numberOfDays) {
        List<Measurement> newMeasurementList = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();

        for (long i = numberOfDays - 1; i >= 0; i--) {
            long startTimeMillis = currentTimeMillis - (i * Constant.ONE_DAY_MILLIS);
            long endTimeMillis = startTimeMillis + Constant.ONE_DAY_MILLIS;
            long datetime = (currentTimeMillis - (i * Constant.ONE_DAY_MILLIS)) / 1000;
            double averageDailyResult = getAverageResultOfMeasurementList(measurementList, startTimeMillis, endTimeMillis);

            newMeasurementList.add(new Measurement(datetime, String.valueOf(averageDailyResult)));
        }

        return newMeasurementList;
    }

    private List<Measurement> filterMeasurementListByNumberOfMonths(List<Measurement> measurementList, long numberOfMonths) {
        List<Measurement> newMeasurementList = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();

        for (long i = numberOfMonths - 1; i >= 0; i--) {
            long startTimeMillis = currentTimeMillis - (i * Constant.ONE_MONTH_MILLIS) - Constant.ONE_MONTH_MILLIS;
            long endTimeMillis = startTimeMillis + Constant.ONE_MONTH_MILLIS;
            long datetime = endTimeMillis / 1000;
            double averageDailyResult = getAverageResultOfMeasurementList(measurementList, startTimeMillis, endTimeMillis);

            newMeasurementList.add(new Measurement(datetime, String.valueOf(averageDailyResult)));
        }

        return newMeasurementList;
    }

    private boolean isWeeklyFilter() {
        return Constant.WEEKLY.equals(filter);
    }

    private boolean isMonthlyFilter() {
        return Constant.MONTHLY.equals(filter);
    }

    private boolean isYearlyFilter() {
        return Constant.YEARLY.equals(filter);
    }
}