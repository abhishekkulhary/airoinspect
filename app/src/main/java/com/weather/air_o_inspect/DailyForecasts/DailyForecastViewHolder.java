package com.weather.air_o_inspect.DailyForecasts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weather.air_o_inspect.R;

public class DailyForecastViewHolder extends RecyclerView.ViewHolder {
    public TextView date_time;
    public TextView sunrise_time;
    public TextView sunset_time;
    public TextView moon_phase;
    public TextView precip_intensity;
    public TextView precip_intensity_max;
    public TextView precip_probability;
    public TextView precip_type;
    public TextView temperature_high;
    public TextView temperature_low;
    public TextView dew_point;
    public TextView humidity;
    public TextView pressure;
    public TextView wind_speed;
    public TextView wind_gust;
    public TextView wind_bearing;
    public TextView cloud_cover;
    public TextView uv_index;
    public TextView visibility;
    public TextView ozone;

    public DailyForecastViewHolder(@NonNull View itemView) {
        super(itemView);
        date_time = itemView.findViewById(R.id.date_time);
        sunrise_time = itemView.findViewById(R.id.sunrise_time);
        sunset_time = itemView.findViewById(R.id.sunset_time);
        moon_phase = itemView.findViewById(R.id.moon_phase);
        precip_intensity = itemView.findViewById(R.id.precip_intensity);
        precip_intensity_max = itemView.findViewById(R.id.precip_intensity_max);
        precip_probability = itemView.findViewById(R.id.precip_probability);
        precip_type = itemView.findViewById(R.id.precip_type);
        temperature_high = itemView.findViewById(R.id.temperature_high);
        temperature_low = itemView.findViewById(R.id.temperature_low);
        dew_point = itemView.findViewById(R.id.dew_point);
        humidity = itemView.findViewById(R.id.humidity);
        pressure = itemView.findViewById(R.id.pressure);
        wind_speed = itemView.findViewById(R.id.wind_speed);
        wind_gust = itemView.findViewById(R.id.wind_gust);
        wind_bearing = itemView.findViewById(R.id.wind_bearing);
        cloud_cover = itemView.findViewById(R.id.cloud_cover);
        uv_index = itemView.findViewById(R.id.uv_index);
        visibility = itemView.findViewById(R.id.visibility);
        ozone = itemView.findViewById(R.id.ozone);
    }

}