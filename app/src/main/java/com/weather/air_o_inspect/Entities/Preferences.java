package com.weather.air_o_inspect.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "preferences")
public class Preferences {

    @PrimaryKey
    private Integer id;
    private Float sunshineThresold;
    private Float temperatureThresold;
    private Float windSpeedThresold;
    private Float windGustThresold;
    private Float precipitationIntensityThresold;
    private Float precipitationProbabilityThresold;

    private Boolean sunshineSwitch;
    private Boolean temperatureSwitch;
    private Boolean windSpeedSwitch;
    private Boolean windGustSwitch;
    private Boolean precipitationIntensitySwitch;
    private Boolean precipitationProbabilitySwitch;

    @Ignore
    public Preferences() {
        this.id = 0;
        this.sunshineThresold = 60.0f;
        this.temperatureThresold = 7.0f;
        this.windSpeedThresold = 4.0f;
        this.windGustThresold = 7.0f;
        this.precipitationIntensityThresold = 0.002f;
        this.precipitationProbabilityThresold = 0.005f;
        this.sunshineSwitch = true;
        this.temperatureSwitch = true;
        this.windSpeedSwitch = true;
        this.windGustSwitch = true;
        this.precipitationIntensitySwitch = true;
        this.precipitationProbabilitySwitch = true;
    }

    public Preferences(Float sunshineThresold, Float temperatureThresold, Float windSpeedThresold,
                       Float windGustThresold, Float precipitationIntensityThresold,
                       Float precipitationProbabilityThresold, Boolean sunshineSwitch,
                       Boolean temperatureSwitch, Boolean windSpeedSwitch, Boolean windGustSwitch,
                       Boolean precipitationIntensitySwitch, Boolean precipitationProbabilitySwitch) {
        this.id = 0;
        this.sunshineThresold = sunshineThresold;
        this.temperatureThresold = temperatureThresold;
        this.windSpeedThresold = windSpeedThresold;
        this.windGustThresold = windGustThresold;
        this.precipitationIntensityThresold = precipitationIntensityThresold;
        this.precipitationProbabilityThresold = precipitationProbabilityThresold;
        this.sunshineSwitch = sunshineSwitch;
        this.temperatureSwitch = temperatureSwitch;
        this.windSpeedSwitch = windSpeedSwitch;
        this.windGustSwitch = windGustSwitch;
        this.precipitationIntensitySwitch = precipitationIntensitySwitch;
        this.precipitationProbabilitySwitch = precipitationProbabilitySwitch;
    }

    public Float getSunshineThresold() {
        return sunshineThresold;
    }

    public Float getTemperatureThresold() {
        return temperatureThresold;
    }

    public Float getWindSpeedThresold() {
        return windSpeedThresold;
    }

    public Float getWindGustThresold() {
        return windGustThresold;
    }

    public Float getPrecipitationIntensityThresold() {
        return precipitationIntensityThresold;
    }

    public Float getPrecipitationProbabilityThresold() {
        return precipitationProbabilityThresold;
    }

    public Boolean getSunshineSwitch() {
        return sunshineSwitch;
    }

    public Boolean getTemperatureSwitch() {
        return temperatureSwitch;
    }

    public Boolean getWindSpeedSwitch() {
        return windSpeedSwitch;
    }

    public Boolean getWindGustSwitch() {
        return windGustSwitch;
    }

    public Boolean getPrecipitationIntensitySwitch() {
        return precipitationIntensitySwitch;
    }

    public Boolean getPrecipitationProbabilitySwitch() {
        return precipitationProbabilitySwitch;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
