package com.app.competence_project_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.competence_project_app.fragment.TemperatureChartFragment;

public class ChartSliderAdapter extends FragmentStateAdapter {

    private final Integer numOfPages;

    public ChartSliderAdapter(@NonNull FragmentActivity activity, Integer numOfPages) {
        super(activity);
        this.numOfPages = numOfPages;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return TemperatureChartFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return numOfPages;
    }
}
