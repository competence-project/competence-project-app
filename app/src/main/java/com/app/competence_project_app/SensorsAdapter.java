package com.app.competence_project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.competence_project_app.model.Sensor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder> {

    private ArrayList<Sensor> localDataSet;
    Context context;
    public SensorsAdapter(Context applicationContext) {
        this.context = applicationContext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewMAC;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.sensorName);
            textViewMAC = (TextView) v.findViewById(R.id.sensorMAC);
        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewMAC() {
            return textViewMAC;
        }
    }

    @NonNull
    @Override
    public SensorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sensors_row, viewGroup, false);

        return new SensorsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorsAdapter.ViewHolder viewHolder, int position) {
        Sensor sensor = localDataSet.get(position);

        viewHolder.getTextViewName().setText(sensor.getLocalization());
        viewHolder.getTextViewMAC().setText(sensor.getMacAddress());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setData(ArrayList<Sensor> list) {
        this.localDataSet = list;
        notifyDataSetChanged();
    }
}
