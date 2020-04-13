package com.weather.air_o_inspect.Charts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.weather.air_o_inspect.R;

public class ChartViewHolder extends RecyclerView.ViewHolder {
    public BarChart chart;
    public TextView chart_name;
    public TextView unit_value;

    public ChartViewHolder(@NonNull View itemView) {
        super(itemView);

        chart = itemView.findViewById(R.id.chart);
        chart_name = itemView.findViewById(R.id.chart_name);
        unit_value = itemView.findViewById(R.id.unit_value);
    }

}