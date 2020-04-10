package com.weather.air_o_inspect.CurrentStatus;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.weather.air_o_inspect.R;

import java.util.List;

public class CurrentStatusViewAdapter extends RecyclerView.Adapter<CurrentStatusViewHolder> {

    private CurrentStatusData currentStatus;


    public CurrentStatusViewAdapter(CurrentStatusData objects) {
        this.currentStatus = objects;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CurrentStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_current_status, parent, false);
        return new CurrentStatusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentStatusViewHolder holder, int position) {
        //getting the product of the specified position
        CurrentStatusData currentStatusData = currentStatus;

        holder.current_fly_status.setText(currentStatusData.getCurrent_fly_status());
        holder.current_rain_status.setText(currentStatusData.getCurrent_rain_status());
        holder.current_temperature.setText(currentStatusData.getCurrent_temperature());
        holder.current_time_place.setText(currentStatusData.getCurrent_time_place());
        holder.current_visibility.setText(currentStatusData.getCurrent_visibility());
        holder.current_wind.setText(currentStatusData.getCurrent_wind());

    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return 1;
    }

}