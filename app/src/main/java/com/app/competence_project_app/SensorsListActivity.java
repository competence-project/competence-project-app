package com.app.competence_project_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.competence_project_app.activities.ConnectedActivity;
import com.app.competence_project_app.model.Sensor;
import com.app.competence_project_app.model.SensorStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SensorsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addButton;

    ClickListener listener;

    List<SensorStore> sensors = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        recyclerView = findViewById(R.id.recyclerViewSensors);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        listener = new ClickListener() {
            @Override
            public void click(int index) {
                Intent intent = new Intent(SensorsListActivity.this, ConnectedActivity.class);
                intent.putExtra("macAddress", sensors.get(index).getMacAddress());
                startActivity(intent);
            }
        };
        recyclerView.setAdapter(new SensorsAdapter(getApplicationContext(), listener));

        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddSensor(view);
            }
        });
    }

    public void goToAddSensor(View view) {
        Intent intent = new Intent(view.getContext(), AddSensorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreSavedData();
        ((SensorsAdapter) recyclerView.getAdapter()).setData(this.sensors);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void restoreSavedData() {
        SensorStore[] sensors = (SensorStore[]) SharedPrefUtility.getDataByName("sensors", SensorStore[].class, this.getApplicationContext());
        if (sensors == null) {
            this.sensors = new ArrayList<>();
            return;
        }

        List<SensorStore> savedSensors = Arrays.asList(sensors);
        if (savedSensors != null) {
            this.sensors = savedSensors;
        }
    }

    private void saveData() {
        SharedPrefUtility.setDataByName("sensors", this.sensors, this.getApplicationContext());
    }
}