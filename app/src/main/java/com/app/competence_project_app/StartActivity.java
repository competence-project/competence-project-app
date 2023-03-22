package com.app.competence_project_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        onButtonBackEventListener();
    }

    private void onButtonBackEventListener() {
        Button button = findViewById(R.id.outlinedButtonBack);
        button.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}