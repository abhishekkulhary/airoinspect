package com.weather.air_o_inspect.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.weather.air_o_inspect.MyApplication;

@Entity(tableName = "weather_forecast")
public class WeatherForecast {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private Long timeInMillis;
    private Float precipIntensity;
    private Float precipProbability;
    private Float temperature;
    private Float pressure;
    private Float windSpeed;
    private Float windGust;
    private Float cloudCover;
    private Float visibility;
    private String date;

    public WeatherForecast(@NonNull Long timeInMillis, @NonNull Float precipIntensity, @NonNull Float precipProbability,
                           @NonNull Float temperature, @NonNull Float pressure, @NonNull Float windSpeed,
                           @NonNull Float windGust, @NonNull Float cloudCover, @NonNull Float visibility) {
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.cloudCover = cloudCover;
        this.visibility = visibility;
        this.timeInMillis = timeInMillis;
        this.date = MyApplication.getSimpleDateFormat().format(timeInMillis * 1000);
    }

    public Float getPrecipIntensity() {
        return precipIntensity;
    }

    public Float getPrecipProbability() {
        return precipProbability;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getPressure() {
        return pressure;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public Float getWindGust() {
        return windGust;
    }

    public Float getCloudCover() {
        return cloudCover;
    }

    public Float getVisibility() {
        return visibility;
    }

    public Long getTimeInMillis() {
        return timeInMillis;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {

        this.date = date;

    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
