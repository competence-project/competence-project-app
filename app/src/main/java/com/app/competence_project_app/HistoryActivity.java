package com.app.competence_project_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.model.TemperatureBarEntry;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.os.Build;
import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {

    private final TemperatureBarEntry temperatureBarEntry = new TemperatureBarEntry();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//
//        BarChart chart = findViewById(R.id.barchart);
//
//        BarDataSet barDataset1 = new BarDataSet(temperatureBarEntry.getTemperatureMin(), "Temperature min");
//        BarDataSet barDataset2 = new BarDataSet(temperatureBarEntry.getTemperatureMax(), "Temperature max");
//        barDataset1.setColor(ColorTemplate.rgb("#EFEBE9"));
//        barDataset2.setColor(ColorTemplate.rgb("#8D6E63"));
//
//        BarData data = new BarData(barDataset1, barDataset2);
//        data.setValueTextColor(ColorTemplate.rgb("#ffffff"));
//        data.setBarWidth(0.2f);
//        data.setValueTextSize(20f);
//
//        chart.setData(data);
//        chart.animateY(5000);
//        chart.getDescription().setEnabled(false);
//
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setAxisLineWidth(3.5f);
//        xAxis.setAxisLineColor(ColorTemplate.rgb("#ffffff"));
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularityEnabled(false);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawLabels(false);
//
//        YAxis yAxisLeft = chart.getAxisLeft();
//        yAxisLeft.setAxisLineWidth(3.5f);
//        yAxisLeft.setTextColor(ColorTemplate.rgb("#ffffff"));
//        yAxisLeft.setAxisLineColor(ColorTemplate.rgb("#ffffff"));
//        yAxisLeft.setGranularityEnabled(false);
//        yAxisLeft.setDrawGridLines(false);
//        yAxisLeft.setTextSize(30f);
//
//        YAxis yAxisRight = chart.getAxisRight();
//        yAxisRight.setDrawAxisLine(false);
//        yAxisRight.setDrawLabels(false);
//
//        chart.setDragEnabled(true);
//        chart.setVisibleXRangeMaximum(2);
//        chart.getXAxis().setAxisMinimum(0);
//        chart.animate();
//        chart.groupBars(0, 0.1f, 0.2f);
//        chart.setDrawGridBackground(false);
//        chart.getLegend().setTextColor(ColorTemplate.rgb("#ffffff"));
    }
}