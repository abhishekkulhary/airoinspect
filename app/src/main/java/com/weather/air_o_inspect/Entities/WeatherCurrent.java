package com.weather.air_o_inspect.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.weather.air_o_inspect.MyApplication;

@Entity(tableName = "weather_current")
public class WeatherCurrent {

    @PrimaryKey
    private Integer id = 1;

    private Long timeInMillis;
    private Float precipIntensity;
    private Float precipProbability;
    private Float temperature;
    private Float pressure;
    private Float windSpeed;
    private Float windGust;
    private Float cloudCover;
    private Float visibility;
    private String dateTime;
    private Double latitude;
    private Double longitude;

    public WeatherCurrent(@NonNull Long timeInMillis, @NonNull Float precipIntensity, @NonNull Float precipProbability,
                          @NonNull Float temperature, @NonNull Float pressure, @NonNull Float windSpeed,
                          @NonNull Float windGust, @NonNull Float cloudCover, @NonNull Float visibility, @NonNull Double latitude, @NonNull Double longitude) {
        this.precipIntensity = precipIntensity;
        this.precipProbability = precipProbability;
        this.temperature = temperature;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windGust = windGust;
        this.cloudCover = cloudCover;
        this.visibility = visibility;
        this.timeInMillis = timeInMillis;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = MyApplication.getSimpleDateWithTimeFormat().format(timeInMillis * 1000);
    }

    public void setDateTime(String dateTime) {

        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
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

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
