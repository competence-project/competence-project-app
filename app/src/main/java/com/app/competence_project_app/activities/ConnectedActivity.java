package com.app.competence_project_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.app.competence_project_app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import java.util.Objects;
import java.util.concurrent.Executors;

public class ConnectedActivity extends AppCompatActivity {

    private Mqtt3AsyncClient client;
    private TextView cmd;
    private TextInputEditText topic;

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
        topic = findViewById(R.id.edittext_topic);

        onButtonRealTimeEventListener();
//        onClickSubscribe();
        onClickPublish();
        onClickDisconnect();
        onClickHistory();
    }

    private void onButtonRealTimeEventListener() {
        Intent intent = new Intent(ConnectedActivity.this, RealTimeActivity.class);
        Button button = findViewById(R.id.outlinedButtonRealTime);
        button.setOnClickListener(view -> {
            startActivity(intent);
        });
    }

//    private void onClickSubscribe() {
//        Button button = findViewById(R.id.outlinedButtonSubscribe);
//        button.setOnClickListener(view -> {
////            String tpc = Objects.requireNonNull(topic.getText()).toString();
//            String tpc = "dev/#";
//            client.subscribeWith()
//                    .topicFilter(tpc)
//                    .qos(MqttQos.AT_LEAST_ONCE)
//                    .callback(response -> {
//                        if(RealTimeActivity.active) {
//                            StringBuilder data = new StringBuilder();
//                            byte[] tab = response.getPayloadAsBytes();
//                            for (byte b : tab) {
//                                data.append((char) b);
//                            }
////                            model.setPayload(new RealTimeViewModel.Payload(
////                                    String.valueOf(data),
////                                    String.valueOf(response.getTopic())
////                            ));
//                            StringContainer stringContainer = (StringContainer) getApplicationContext();
//                            stringContainer.setTopicData(String.valueOf(response.getTopic()), String.valueOf(data));
//                            System.out.println(response);
//                        } else {
//                            System.out.println("Przyjeto jakies dane, ale activity jest wylaczone");
//                        }
//                    })
//                    .send()
//                    .whenComplete((subAck, throwable) -> {
//                        if (throwable != null) {
////                                System.out.println("PORAŻKA");
////                                cmd.append("failure - subscribe on topic: " + topic.getText().toString() + "\n");
//                            Executors.newSingleThreadExecutor().execute(() -> {
//                                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
//                            });
//                        } else {
////                                System.out.println("SUKCES");
////                                cmd.append("success - subscribe on topic: " + topic.getText().toString() + "\n");
////                                Executors.newSingleThreadExecutor().execute(() -> {Toast.makeText(this,"success",Toast.LENGTH_SHORT).show();});
////                            Intent intent = new Intent(ConnectedActivity.this, RealTimeActivity.class);
////                            startActivity(intent);
//                        }
//                    });
//            }
//        );
//    }

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
                                cmd.append("failure - publish on topic: " + topic.getText().toString() + "\n");
                            } else {
                                cmd.append("success - publish on topic: " + topic.getText().toString() + "\n");
                            }
                        })
        );
    }

    private void onClickDisconnect() {
        ImageView imageViewButton = findViewById(R.id.outlinedButtonDisconnect);
        imageViewButton.setOnClickListener(view -> {
            client.disconnect();
            cmd.append("disconnected\n");
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
