package com.app.competence_project_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.R;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.util.concurrent.Executors;

public class RealTimeActivity extends AppCompatActivity {

    private Mqtt3AsyncClient client;

    static boolean active = false;

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);

        client = StartActivity.getClient();
        subscribe();

        onClickMoveToHistoryActivity(R.id.temperature_value, 0);
        onClickMoveToHistoryActivity(R.id.luminance_value, 1);
        onClickMoveToHistoryActivity(R.id.humidity_value, 2);
        onClickMoveToHistoryActivity(R.id.pressure_value, 3);

    }

    private void onClickMoveToHistoryActivity(int buttonID, int slidePage) {
        Intent intent = new Intent(RealTimeActivity.this, HistoryActivity.class);
        intent.putExtra("slidePage", slidePage);
        Button button = findViewById(buttonID);
        button.setOnClickListener(view -> startActivity(intent));
    }

    private void subscribe() {
        Button button = findViewById(R.id.outlinedButtonSubscribe);
        button.setOnClickListener(view -> {
//            String tpc = Objects.requireNonNull(topic.getText()).toString();
            String tpc = "dev/#";
            client.subscribeWith()
                    .topicFilter(tpc)
                    .qos(MqttQos.AT_LEAST_ONCE)
                    .callback(response -> {
                        if(RealTimeActivity.active) {
                            StringBuilder data = new StringBuilder();
                            byte[] tab = response.getPayloadAsBytes();
                            for (byte b : tab) {
                                data.append((char) b);
                            }
                            setCurrentValue(String.valueOf(data), String.valueOf(response.getTopic()));
                            System.out.println(response);
                        } else {
                            System.out.println("Przyjeto jakies dane, ale activity jest wylaczone");
                        }
                    })
                    .send()
                    .whenComplete((subAck, throwable) -> {
                        if (throwable != null) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();
                            });
                        }
                    });
            }
        );
    }

    private void setCurrentValue(String data, String topic) {

        Button button = null;
        if(topic.contains("temp")) {
            button = findViewById(R.id.temperature_value);
        } else if(topic.contains("lum")) {
            button = findViewById(R.id.luminance_value);
        } else if(topic.contains("hum")) {
            button = findViewById(R.id.humidity_value);
        } else if(topic.contains("press")) {
            button = findViewById(R.id.pressure_value);
        }

        if(button != null) {
            button.setText(data);
        }
    }
}
