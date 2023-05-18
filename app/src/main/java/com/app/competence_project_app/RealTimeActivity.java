package com.app.competence_project_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RealTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time);

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
}
