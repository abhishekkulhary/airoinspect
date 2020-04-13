package com.weather.air_o_inspect.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.weather.air_o_inspect.MyApp;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Entity(tableName = "current_status")
public class CurrentStatus {

    private String currentFlyStatus;
    private String currentTime;

    @PrimaryKey
    private Long time;
    private Float precipIntensity;
    private Float precipProbability;
    private Float temperature;
    private Float pressure;
    private Float windSpeed;
    private Float windGust;
    private Float cloudCover;
    private Float visibility;

    public CurrentStatus(Long time, Float precipIntensity, Float precipProbability,
                         Float temperature, Float pressure, Float windSpeed,
                         Float windGust, Float cloudCover, Float visibility) {
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.cloudCover = cloudCover;
        this.visibility = visibility;
        this.time = time;
    }

    public void setCurrentFlyStatus(String currentFlyStatus) {
        this.currentFlyStatus = currentFlyStatus;
    }

    public void setCurrentTime(Long time) {

        this.setCurrentTime(MyApp.getSimpleDateWithTimeFormat().format(time * 1000));
    }

    public void setCurrentTime(String currentTime) {

        this.currentTime = currentTime;
    }

    public String getCurrentFlyStatus() {
        return currentFlyStatus;
    }

    public String getCurrentTime() {
        return currentTime;
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

    public Long getTime() {
        return time;
    }
}
