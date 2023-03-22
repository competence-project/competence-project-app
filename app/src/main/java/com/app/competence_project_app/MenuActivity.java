package com.app.competence_project_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        onButtonStartEventListener();
        onButtonExitEventListener();
    }

    private void onButtonStartEventListener() {
        Intent intent = new Intent(MenuActivity.this, StartActivity.class);
        Button button = findViewById(R.id.outlinedButtonSimple);
        button.setOnClickListener(view -> {
            startActivity(intent);
        });
    }

    private void onButtonExitEventListener() {
        Button button = findViewById(R.id.outlinedButtonExit);
        button.setOnClickListener(view -> {
            finish();
            System.exit(0);
        });
    }
}