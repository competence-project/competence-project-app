package com.app.competence_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

public class ConnectedActivity extends AppCompatActivity {

    private Mqtt3AsyncClient client;
    private TextView cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_pub);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        client = StartActivity.getClient();
        cmd = findViewById(R.id.edittext_result);

        onButtonBackEventListener();
        onClickSubscribe();
        onClickPublish();
        onClickDisconnect();
        onClickHistory();
    }

    private void onButtonBackEventListener() {
        Button button = findViewById(R.id.outlinedButtonBack);
        button.setOnClickListener(view -> onBackPressed());
    }

    private void onClickSubscribe() {
        Button button = findViewById(R.id.outlinedButtonSubscribe);
        button.setOnClickListener(view ->
                client.subscribeWith()
                        .topicFilter("my/topic")
                        .qos(MqttQos.AT_LEAST_ONCE)
                        .callback(response -> cmd.append(response + "\n"))
                        .send()
                        .whenComplete((subAck, throwable) -> {
                            if (throwable != null) {
                                cmd.append("failure - subscribe\n");
                            } else {
                                cmd.append("success - subscribe\n");
                            }
                        })
        );
    }

    private void onClickPublish() {
        Button button = findViewById(R.id.outlinedButtonPublish);
        button.setOnClickListener(view ->
                client.publishWith()
                        .topic("my/topic")
                        .payload("1".getBytes())
                        .qos(MqttQos.EXACTLY_ONCE)
                        .send()
                        .whenComplete((mqtt3Publish, throwable1) -> {
                            if (throwable1 != null) {
                                cmd.append("failure - publish\n");
                            } else {
                                cmd.append("success - publish\n");
                            }
                        })
        );
    }

    private void onClickDisconnect() {
        Button button = findViewById(R.id.outlinedButtonDisconnect);
        button.setOnClickListener(view -> {
            client.disconnect();
            cmd.append("disconnected\n");
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
