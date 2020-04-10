package com.weather.air_o_inspect.CurrentStatus;

import android.util.Log;

import com.weather.air_o_inspect.MyApp;

import java.util.List;
import java.util.Map;

public class CurrentStatusData {
    private String current_fly_status;
    private String current_temperature;
    private String current_rain_status;
    private String current_wind;
    private String current_visibility;
    private String current_time_place;

    public String getCurrent_fly_status() {
        return current_fly_status;
    }

    public void setCurrent_fly_status(String current_fly_status) {
        this.current_fly_status = current_fly_status;
    }

    public String getCurrent_temperature() {
        return current_temperature;
    }

    public void setCurrent_temperature(String current_temperature) {
        this.current_temperature = current_temperature;
    }

    public String getCurrent_rain_status() {
        return current_rain_status;
    }

    public void setCurrent_rain_status(String current_rain_status) {
        this.current_rain_status = current_rain_status;
    }

    public String getCurrent_wind() {
        return current_wind;
    }

    public void setCurrent_wind(String current_wind) {
        this.current_wind = current_wind;
    }

    public String getCurrent_visibility() {
        return current_visibility;
    }

    public void setCurrent_visibility(String current_visibility) {
        this.current_visibility = current_visibility;
    }

    public String getCurrent_time_place() {
        return current_time_place;
    }

    public void setCurrent_time_place(String current_time_place) {
        this.current_time_place = current_time_place;
    }

    public void populatateCurrentStatus(Map<String, List<String>> currentWeatherCondition, MyApp myApp){
        if (currentWeatherCondition != null && !currentWeatherCondition.isEmpty()) {
            Log.i("pupulateCurrentstatus","value not null");
            this.setCurrent_time_place(
                    myApp.getSimpleDateFormat().format(Long.parseLong(currentWeatherCondition.get("values")
                            .get(currentWeatherCondition.get("titles").indexOf("time"))))
                            + " " + myApp.getSimpleTimesFormat().format(Long.parseLong(
                            currentWeatherCondition.get("values")
                                    .get(currentWeatherCondition.get("titles").indexOf("time"))) * 1000));
            this.setCurrent_rain_status("Precipitation: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("precipIntensity")));
            this.setCurrent_temperature("Temperature: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("temperature")));
            this.setCurrent_visibility("Sun or Visibility: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("visibility")));
            this.setCurrent_wind("Wind Speed: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("windSpeed")));

        }
    }
}
