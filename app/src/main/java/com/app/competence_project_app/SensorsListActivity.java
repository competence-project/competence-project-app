package com.app.competence_project_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.competence_project_app.model.Sensor;

import java.util.ArrayList;

public class SensorsListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list);

        recyclerView = findViewById(R.id.recyclerViewSensors);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new SensorsAdapter(getApplicationContext()));

        // dummy content
        Sensor sensor1 = new Sensor("d4:50:68:73:72:e0", "Budynek Alpha");
        Sensor sensor2 = new Sensor("c3:53:87:f3:46:07", "Budynek Beta");
        Sensor sensor3 = new Sensor("a8:f4:d6:ce:9f:ab", "Budynek Gamma");
        Sensor sensor4 = new Sensor("21:16:ec:d4:6b:4a", "Budynek Delta");

        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors.add(sensor1);
        sensors.add(sensor2);
        sensors.add(sensor3);
        sensors.add(sensor4);

        ((SensorsAdapter) recyclerView.getAdapter()).setData(sensors);
    }
}