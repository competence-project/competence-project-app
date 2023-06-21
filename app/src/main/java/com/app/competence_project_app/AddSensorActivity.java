package com.app.competence_project_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.competence_project_app.model.SensorStore;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddSensorActivity extends AppCompatActivity {

    List<SensorStore> sensors;
    Button addSensorButton;
    private Mqtt3AsyncClient client;

    EditText macEditText;
    EditText nameEditText;

    private String topic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        macEditText = findViewById(R.id.sensor_mac);
        nameEditText = findViewById(R.id.sensor_name);
        addSensorButton = findViewById(R.id.outlinedButtonAdd);
        addSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String macAddress = macEditText.getText().toString();
                String name = nameEditText.getText().toString();
                if (name.equals("") || macAddress.equals("")) {
                    Toast.makeText(AddSensorActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                SensorStore foundSensor = sensors.stream().filter(sensor -> sensor.getMacAddress().equals(macAddress)).findAny().orElse(null);
                if (foundSensor != null) {
                    Toast.makeText(AddSensorActivity.this, "Sensor with given MAC address already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                foundSensor = sensors.stream().filter(sensor -> sensor.getLocalization().equals(name)).findAny().orElse(null);
                if (foundSensor != null) {
                    Toast.makeText(AddSensorActivity.this, "Sensor with given name already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                // TODO: subscribe to check if MAC address is correct

                sensors.add(new SensorStore(macAddress, name));
                Toast.makeText(AddSensorActivity.this, "Sensor added", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        restoreSavedData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void restoreSavedData() {
        SensorStore[] sensors = (SensorStore[]) SharedPrefUtility.getDataByName("sensors", SensorStore[].class, this.getApplicationContext());
        if (sensors == null || sensors.length < 1) {
            this.sensors = new ArrayList<>();
            return;
        }

        new ArrayList<>(Arrays.asList(sensors));
    }

    private void saveData() {
        SharedPrefUtility.setDataByName("sensors", this.sensors, this.getApplicationContext());
    }

    private void clearInputs() {
        this.macEditText.setText("");
        this.nameEditText.setText("");
    }

    private void subscribe() {
        client.subscribeWith()
                .topicFilter(topic)
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(response -> {

                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast toast = Toast.makeText(getApplicationContext(), "subscription failed", Toast.LENGTH_SHORT);
                            toast.show();
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast toast = Toast.makeText(getApplicationContext(), "subscription successful", Toast.LENGTH_SHORT);
                            toast.show();
                        });
                    }
                });
    }
}