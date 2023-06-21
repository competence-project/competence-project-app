package com.app.competence_project_app.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.R;
import com.app.competence_project_app.SensorsListActivity;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImpl;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3ConnAckException;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class StartActivity extends AppCompatActivity {

    private static Mqtt3AsyncClient client;
    private String clientId;

    public static Mqtt3AsyncClient getClient() {
        return client;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        onButtonCleanEventListener();
    }

    private void onButtonCleanEventListener() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onButtonConnectEventListener() {
        Button button = findViewById(R.id.outlinedButtonConnect);
        button.setOnClickListener(view -> {
            connectToBroker();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void connectToBroker() {
        String text = ((EditText) findViewById(R.id.edittext_server_uri)).getText().toString();
        if(text.length() == 0) {
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast toast = Toast.makeText(getApplicationContext(), "blank uri", Toast.LENGTH_SHORT);
                toast.show();
            });
            return;
        }

        client = MqttClient.builder()
                .useMqttVersion3()
                .identifier(clientId)
                .addDisconnectedListener(context -> {
                    final Throwable cause = context.getCause();
                    if (cause instanceof Mqtt3ConnAckException) {
                        System.out.println("disconnect listener: Connect failed because of Negative CONNACK with code {} " + ((Mqtt3ConnAckException) cause).getMqttMessage().getReturnCode());
                    } else {
                        System.out.println("disconnect listener: MQTT connect failed because of {}" + cause.getMessage());
                    }
                })
                .serverHost(text)
                .buildAsync();

        client.connectWith()
                .simpleAuth()
                .username("admin")
                .password("admin".getBytes())
                .applySimpleAuth()
                .send()
                .whenComplete((connAck, throwable) -> {
                    if (connAck != null) {
                        System.out.println("whenComplete: MQTT connect success with code {}" + connAck.getReturnCode());
                    } else {
                        if (throwable instanceof Mqtt3ConnAckException) {
                            System.out.println("whenComplete: Connect failed because of Negative CONNACK with code {} " + ((Mqtt3ConnAckException) throwable).getMqttMessage().getReturnCode());
                        } else {
                            System.out.println("whenComplete: MQTT connect failed because of {}" + throwable.getMessage());
                        }
                    }
                });

        while(true) {
            if(client.getState() == MqttClientState.CONNECTED) {
                Intent intent = new Intent(this, SensorsListActivity.class);
                startActivity(intent);
                break;
            } else if(client.getState() == MqttClientState.DISCONNECTED) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast toast = Toast.makeText(getApplicationContext(), "failed to connect", Toast.LENGTH_SHORT);
                    toast.show();
                });
                break;
            }
        }
    }
}