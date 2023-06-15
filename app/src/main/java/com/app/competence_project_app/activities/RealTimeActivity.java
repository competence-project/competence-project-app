package com.app.competence_project_app.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.R;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;

import java.util.List;
import java.util.concurrent.Executors;

public class RealTimeActivity extends AppCompatActivity {

    private Mqtt3AsyncClient client;
    private String topic = "dev/#";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        client = StartActivity.getClient();
        subscribe();

        onClickMoveToHistoryActivity(R.id.temperature_value, 0);
        onClickMoveToHistoryActivity(R.id.luminance_value, 1);
        onClickMoveToHistoryActivity(R.id.humidity_value, 2);
        onClickMoveToHistoryActivity(R.id.pressure_value, 3);

    }

//    @Override
//    public void onBackPressed() {
//        Mqtt3Unsubscribe unsubscribe = Mqtt3Unsubscribe.builder()
//                .topicFilter(topic)
//                .build();
//        client.unsubscribe(unsubscribe);
//    }

    private void onClickMoveToHistoryActivity(int buttonID, int slidePage) {
        Intent intent = new Intent(RealTimeActivity.this, HistoryActivity.class);
        intent.putExtra("slidePage", slidePage);
        Button button = findViewById(buttonID);
        button.setOnClickListener(view -> startActivity(intent));
    }

    private void subscribe() {
        client.subscribeWith()
                .topicFilter(topic)
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback(response -> {
                    StringBuilder data = new StringBuilder();
                    byte[] tab = response.getPayloadAsBytes();
                    for (byte b : tab) {
                        data.append((char) b);
                    }
                    if(data.length() > 0) {
                        setCurrentValue(String.valueOf(data), String.valueOf(response.getTopic()));
                        System.out.println(response);
                    }
                })
                .send()
                .whenComplete((subAck, throwable) -> {
                    if (throwable != null) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast toast = Toast.makeText(getApplicationContext(), "subscription failed", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(RealTimeActivity.this, ConnectedActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast toast = Toast.makeText(getApplicationContext(), "subscription successful", Toast.LENGTH_SHORT);
                            toast.show();
                        });
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    private void setCurrentValue(String data, String topic) {

        Button button = null;
        if(topic.contains("temp")) {
            button = findViewById(R.id.temperature_value);
            double tempInKelvin = Double.parseDouble(data);
            double tempInCelsius = tempInKelvin - 273.15;
            data = String.format("%.1f", tempInCelsius);
            data += "°C";
        } else if(topic.contains("lum")) {
            button = findViewById(R.id.luminance_value);
            double lux = Double.parseDouble(data);
            data = String.format("%.1f", lux);
            data += "\nlux";
        } else if(topic.contains("hum")) {
            button = findViewById(R.id.humidity_value);
            data += "%";
        } else if(topic.contains("pssr")) {
            button = findViewById(R.id.pressure_value);
            double pssrInPa = Double.parseDouble(data);
            data = String.format("%.0f", pssrInPa/100);
            data += "\nhPa";
        }

        if(button != null) {
            button.setText(data);
        }
    }
}
