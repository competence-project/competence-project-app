package com.app.competence_project_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.competence_project_app.model.SensorStore;

import java.util.ArrayList;
import java.util.List;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder> {
    ClickListener listener;

    private ArrayList<SensorStore> localDataSet;
    Context context;
    public SensorsAdapter(Context applicationContext, ClickListener listener) {
        this.context = applicationContext;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewMAC;

        public ViewHolder(View v, ClickListener listener) {
            super(v);
            v.setOnClickListener(view -> listener.click(this.getAdapterPosition()));
            textViewName = (TextView) v.findViewById(R.id.sensorName);
            textViewMAC = (TextView) v.findViewById(R.id.sensorMAC);
            ImageButton imageButton = v.findViewById(R.id.removeButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Delete ".concat(localDataSet.get(getAdapterPosition()).getLocalization()).concat("?"))
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    localDataSet.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    Toast.makeText(v.getContext(), "Deleted", Toast.LENGTH_SHORT).show();
                                }

                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .show();
                }
            });
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

        viewHolder.getTextViewName().setText("Name: ".concat(sensor.getLocalization()));
        viewHolder.getTextViewMAC().setText("MAC address: ".concat(sensor.getMacAddress()));
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

    public void setData(ArrayList<SensorStore> list) {
        this.localDataSet = list;
        notifyDataSetChanged();
    }
}
