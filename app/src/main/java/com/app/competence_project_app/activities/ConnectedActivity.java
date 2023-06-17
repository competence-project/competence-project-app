package com.app.competence_project_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.util.Objects;

public class ConnectedActivity extends AppCompatActivity {

    private Mqtt3AsyncClient client;
    private TextInputEditText topic;

    private String macAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        macAddress = intent.getStringExtra("macAddress");

        setContentView(R.layout.activity_connected);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        client = StartActivity.getClient();
        topic = findViewById(R.id.edittext_topic);

        onButtonRealTimeEventListener();
        onClickPublish();
        onClickDisconnect();
        onClickHistory();
    }

    private void onButtonRealTimeEventListener() {
        Intent intent = new Intent(ConnectedActivity.this, RealTimeActivity.class);
        intent.putExtra("macAddress", macAddress);
        Button button = findViewById(R.id.outlinedButtonRealTime);
        button.setOnClickListener(view -> {
            startActivity(intent);
        });
    }

    private void onClickPublish() {
        Button button = findViewById(R.id.outlinedButtonPublish);
        button.setOnClickListener(view ->
                client.publishWith()
                        .topic(Objects.requireNonNull(topic.getText()).toString())
                        .payload("1".getBytes())
                        .qos(MqttQos.EXACTLY_ONCE)
                        .send()
                        .whenComplete((mqtt3Publish, throwable1) -> {
                            if (throwable1 != null) {
                                System.out.println("failure - publish on topic: " + topic.getText().toString() + "\n");
                            } else {
                                System.out.println("success - publish on topic: " + topic.getText().toString() + "\n");
                            }
                        })
        );
    }

    private void onClickDisconnect() {
        ImageView imageViewButton = findViewById(R.id.outlinedButtonDisconnect);
        imageViewButton.setOnClickListener(view -> {
            client.disconnect();
            System.out.println("disconnected\n");
            Intent intent = new Intent(ConnectedActivity.this, StartActivity.class);
            startActivity(intent);
        });
    }

    private void onClickHistory() {
        Intent intent = new Intent(ConnectedActivity.this, HistoryActivity.class);
        Button button = findViewById(R.id.outlinedButtonHistory);
        button.setOnClickListener(view -> {
            startActivity(intent);
        });
    }
}
