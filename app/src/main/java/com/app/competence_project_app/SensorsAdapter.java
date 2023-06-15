package com.app.competence_project_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.competence_project_app.model.SensorStore;

import java.util.List;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder> {
    ClickListener listener;

    private List<SensorStore> localDataSet;
    Context context;
    public SensorsAdapter(Context applicationContext, ClickListener listener) {
        this.context = applicationContext;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewMAC;

        public ViewHolder(View v, ClickListener listener) {
            super(v);
            v.setOnClickListener(view -> listener.click(this.getAdapterPosition()));
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

        return new SensorsAdapter.ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorsAdapter.ViewHolder viewHolder, int position) {
        SensorStore sensor = localDataSet.get(position);

        viewHolder.getTextViewName().setText(sensor.getLocalization());
        viewHolder.getTextViewMAC().setText(sensor.getMacAddress());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public void setData(List<SensorStore> list) {
        this.localDataSet = list;
        notifyDataSetChanged();
    }
}
