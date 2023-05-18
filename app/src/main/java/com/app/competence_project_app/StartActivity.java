package com.app.competence_project_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.util.UUID;

public class StartActivity extends AppCompatActivity {

    private String clientId;
    private static Mqtt3AsyncClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        clientId = UUID.randomUUID().toString();

        onButtonConnectEventListener();
        onButtonBackEventListener();
        onButtonCleanEventListener();
    }

    public void onButtonBackEventListener() {
        Button button = findViewById(R.id.outlinedButtonRealTime);
        button.setOnClickListener(view -> onBackPressed());
    }

    private void onButtonCleanEventListener() {
    }

    private void onButtonConnectEventListener() {
        Button button = findViewById(R.id.outlinedButtonConnect);
        button.setOnClickListener(view -> {
            connectToBroker();
        });
    }

    private void connectToBroker() {
        String text = ((EditText)findViewById(R.id.edittext_server_uri)).getText().toString();
        System.out.println(text);
        client = MqttClient.builder()
                .useMqttVersion3()
                .identifier(clientId)
                .serverHost(text)
                .buildAsync();

        client.connect();


        Intent intent = new Intent(this, ConnectedActivity.class);
        startActivity(intent);
    }

    public static Mqtt3AsyncClient getClient() {
        return client;
    }
}