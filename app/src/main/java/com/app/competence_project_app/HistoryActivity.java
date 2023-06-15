package com.app.competence_project_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.app.competence_project_app.adapter.ChartSliderAdapter;

import android.os.Build;
import android.os.Bundle;

public class HistoryActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ViewPager2 viewPager = findViewById(R.id.pager);
        Integer numOfPages = 4;
        FragmentStateAdapter pagerAdapter = new ChartSliderAdapter(this, numOfPages);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setFocusedByDefault(false);
        viewPager.setCurrentItem(0);
    }
}