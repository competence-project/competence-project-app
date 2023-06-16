package com.app.competence_project_app.activities;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.app.competence_project_app.R;
import com.app.competence_project_app.adapter.ChartSliderAdapter;
import com.app.competence_project_app.util.constant.Constant;

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

        int currentItem = 0;
        String macAddress = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentItem = extras.getInt("slidePage");
            macAddress = extras.getString(Constant.MAC_ADDRESS);
        }

        ViewPager2 viewPager = findViewById(R.id.pager);
        Integer numOfPages = 4;
        FragmentStateAdapter pagerAdapter = new ChartSliderAdapter(this, numOfPages, macAddress);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setFocusedByDefault(false);
        viewPager.setCurrentItem(currentItem);
    }
}