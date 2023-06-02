package com.app.competence_project_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.app.competence_project_app.MVVM.RealTimeViewModel;
import com.app.competence_project_app.R;

public class RealTimeActivity extends AppCompatActivity {
    private RealTimeViewModel model;
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
        model = new ViewModelProvider(this).get(RealTimeViewModel.class);

        onClickMoveToHistoryActivity(R.id.temperature_value, 0);
        onClickMoveToHistoryActivity(R.id.luminance_value, 1);
        onClickMoveToHistoryActivity(R.id.humidity_value, 2);
        onClickMoveToHistoryActivity(R.id.pressure_value, 3);

        model.getPayload().observe(this, payload -> {
            setCurrentValue(payload.data, payload.topic);
        });
    }

    private void onClickMoveToHistoryActivity(int buttonID, int slidePage) {
        Intent intent = new Intent(RealTimeActivity.this, HistoryActivity.class);
        intent.putExtra("slidePage", slidePage);
        Button button = findViewById(buttonID);
        button.setOnClickListener(view -> startActivity(intent));
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
