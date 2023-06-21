package com.app.competence_project_app.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.R;
import com.app.competence_project_app.SensorsAdapter;
import com.app.competence_project_app.SharedPrefUtility;
import com.app.competence_project_app.model.SensorStore;
import com.app.competence_project_app.util.constant.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConnectedActivity extends AppCompatActivity {

    private Mqtt3AsyncClient client;
    private TextInputEditText topic;
    private TextInputEditText message;
    private String macAddress;

    ArrayList<SensorStore> sensors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        macAddress = intent.getStringExtra(Constant.MAC_ADDRESS);

        setContentView(R.layout.activity_connected);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        client = StartActivity.getClient();
        topic = findViewById(R.id.edittext_topic);
        message = findViewById(R.id.edittext_message);

        onButtonRealTimeEventListener();
        onClickPublish();
        onClickDisconnect();
        onClickHistory();
        onClickDelete();
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
        if (sensors == null) {
            this.sensors = new ArrayList<>();
            return;
        }

        this.sensors = new ArrayList<>(Arrays.asList(sensors));
    }

    private void saveData() {
        SharedPrefUtility.setDataByName("sensors", this.sensors, this.getApplicationContext());
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
        button.setOnClickListener(view -> {
            String msg = String.valueOf(message.getText());
            String tpc = String.valueOf(topic.getText());
            if(msg.length() != 0 && tpc.length() != 0) {
                client.publishWith()
                        .topic(tpc)
                        .payload(msg.getBytes())
                        .qos(MqttQos.EXACTLY_ONCE)
                        .send()
                        .whenComplete((mqtt3Publish, throwable1) -> {
                            if (throwable1 != null) {
                                System.out.println("failure - publish on topic: " + tpc + "\n");
                            } else {
                                System.out.println("success - publish on topic: " + tpc + "\n");
                            }
                        });
            }
        });
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
        intent.putExtra(Constant.MAC_ADDRESS, macAddress);
        Button button = findViewById(R.id.outlinedButtonHistory);
        button.setOnClickListener(view -> {
            startActivity(intent);
        });
    }

    private void onClickDelete() {
        Button button = findViewById(R.id.outlinedButtonDelete);
        button.setOnClickListener(view -> {
            new AlertDialog.Builder(ConnectedActivity.this)
                    .setTitle("Delete sensor?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SensorStore sensorToRemove = sensors.stream().filter(sensor -> sensor.getMacAddress().equals(macAddress)).findFirst().orElse(null);
                            if (sensorToRemove == null) {
                                Toast.makeText(getApplicationContext(), "Already deleted", Toast.LENGTH_SHORT).show();
                            }

                            sensors.remove(sensorToRemove);
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    })
                    .show();
        });
    }
}
