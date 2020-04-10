package com.weather.air_o_inspect.CurrentStatus;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.weather.air_o_inspect.R;

public class CurrentStatusViewHolder extends RecyclerView.ViewHolder {
    public TextView current_fly_status;
    public TextView current_temperature;
    public TextView current_rain_status;
    public TextView current_wind;
    public TextView current_visibility;
    public TextView current_time_place;

    public CurrentStatusViewHolder(@NonNull View itemView) {
        super(itemView);

        current_fly_status = itemView.findViewById(R.id.current_fly_status);
        current_temperature = itemView.findViewById(R.id.current_temperature);
        current_rain_status = itemView.findViewById(R.id.current_rain_status);
        current_wind = itemView.findViewById(R.id.current_wind);
        current_visibility = itemView.findViewById(R.id.current_visibility);
        current_time_place = itemView.findViewById(R.id.current_time_place);

    }

}