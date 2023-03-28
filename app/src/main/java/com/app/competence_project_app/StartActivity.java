package com.app.competence_project_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

// import com.hivemq.client.mqtt.*;

public class StartActivity extends AppCompatActivity {

//    private String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        clientId = MqttClient.generateClientId();
//
//        onButtonConnectEventListener();
        onButtonBackEventListener();
    }

    private void onButtonBackEventListener() {
        Button button = findViewById(R.id.outlinedButtonBack);
        button.setOnClickListener(view -> {
            onBackPressed();
        });
    }

//    private void onButtonConnectEventListener() {
//        Button button = findViewById(R.id.outlinedButtonConnect);
//        button.setOnClickListener(view -> {
//            TextInputEditText textInputEditText = findViewById(R.id.edittext_server_uri);
//            String serverUri = String.valueOf(textInputEditText.getText());
//
//            if (!serverUri.isEmpty()) {
//                connectToBroker(serverUri);
//            }
//        });
//    }
//
//    private void connectToBroker(String serverUri) {
//        try {
//            MqttAndroidClient client = new MqttAndroidClient(this.getApplicationContext(), serverUri, clientId);
//            IMqttToken token = client.connect();
//            token.setActionCallback(new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Log.d(TAG, "Success");
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Log.d(TAG, "Failure");
//                }
//            });
//        } catch (MqttException e) {
//            e.printStackTrace();
//        }
//    }
}