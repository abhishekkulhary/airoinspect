package com.weather.air_o_inspect.DailyForecasts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.weather.air_o_inspect.Entities.WeatherForecastDaily;
import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.R;

import java.util.List;

public class DailyForecastDataAdapter extends RecyclerView.Adapter<DailyForecastViewHolder> {
    private List<WeatherForecastDaily> weatherForecastDailyList;

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public DailyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_forecast_daily, parent, false);
        return new DailyForecastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyForecastViewHolder holder, int position) {
        //getting the product of the specified position

        WeatherForecastDaily forecastDaily = this.weatherForecastDailyList.get(position);
        if (forecastDaily != null) {

            holder.date_time.setText(MyApplication.getSimpleDateWithTimeFormat().format(forecastDaily.getTimeInMillis() * 1000));
            holder.sunrise_time.setText("Sunrise Time : " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getSunriseTimeInMillis() * 1000));
            holder.sunset_time.setText("Sunset Time : " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getSunsetTimeInMillis() * 1000));

            holder.moon_phase.setText("Moon Phase : " + forecastDaily.getMoonPhase());
            holder.precip_intensity.setText("Precipitation Intensity : " + forecastDaily.getPrecipIntensity());

            holder.precip_intensity_max.setText("Max. Precipitation Intensity : " + forecastDaily.getPrecipIntensityMax() + " At " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getPrecipIntensityMaxTimeInMillis() * 1000));

            holder.precip_probability.setText("Precipitation Probability : " + forecastDaily.getPrecipProbability());
            holder.precip_type.setText("Precipitation Type : " + forecastDaily.getPrecipType());

            holder.temperature_high.setText("Temperature High : " + forecastDaily.getTemperatureHigh() + " At " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getTemperatureHighTimeInMillis() * 1000));

            holder.temperature_low.setText("Temperature Low : " + forecastDaily.getTemperatureLow() + " At " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getTemperatureLowTimeInMillis() * 1000));

            holder.dew_point.setText("Dew point : " + forecastDaily.getDewPoint());
            holder.humidity.setText("Humidity : " + forecastDaily.getHumidity());
            holder.pressure.setText("Pressure : " + forecastDaily.getPressure());
            holder.wind_speed.setText("Wind Speed : " + forecastDaily.getWindSpeed());

            holder.wind_gust.setText("Wind Gust : " + forecastDaily.getWindGust() + " At " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getWindGustTimeInMillis() * 1000));

            holder.wind_bearing.setText("Wind Bearing : " + forecastDaily.getWindBearing());
            holder.cloud_cover.setText("Cloud Cover : " + forecastDaily.getCloudCover());

            holder.uv_index.setText("UV Index : " + forecastDaily.getUvIndex() + " At " + MyApplication.getSimpleTimeWithMinFormat().format(forecastDaily.getUvIndexTimeInMillis() * 1000));

            holder.visibility.setText("Visibility : " + forecastDaily.getVisibility());
            holder.ozone.setText("Ozone : " + forecastDaily.getOzone());
        }

    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public int getItemCount() {
        return this.weatherForecastDailyList.size();
    }

    public void setDailyForecastList(List<WeatherForecastDaily> weatherForecastDailyList) {
        this.weatherForecastDailyList = weatherForecastDailyList;
        notifyDataSetChanged();
    }
}