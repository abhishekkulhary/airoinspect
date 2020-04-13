package com.weather.air_o_inspect.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "preferences")
public class Preferences {
    @NonNull
    @PrimaryKey
    private Float precipitationIntensityThresold;
    private Float precipitationProbabilityThresold;
    private Float temperatureThresold;
    private Float pressureThresold;
    private Float windSpeedThresold;
    private Float windGustThresold;
    private Float cloudCoverThresold;
    private Float visibilityThresold;

    private Boolean precipitationIntensitySwitch;
    private Boolean precipitationProbabilitySwitch;
    private Boolean temperatureSwitch;
    private Boolean pressureSwitch;
    private Boolean windSpeedSwitch;
    private Boolean windGustSwitch;
    private Boolean cloudCoverSwitch;
    private Boolean visibilitySwitch;

    public Preferences(@NotNull Float precipitationIntensityThresold, Float precipitationProbabilityThresold,
                       Float temperatureThresold, Float pressureThresold, Float windSpeedThresold, Float windGustThresold,
                       Float cloudCoverThresold, Float visibilityThresold, Boolean precipitationIntensitySwitch,
                       Boolean precipitationProbabilitySwitch, Boolean temperatureSwitch, Boolean pressureSwitch,
                       Boolean windSpeedSwitch, Boolean windGustSwitch, Boolean cloudCoverSwitch, Boolean visibilitySwitch) {
        this.precipitationIntensityThresold = precipitationIntensityThresold;
        this.precipitationProbabilityThresold = precipitationProbabilityThresold;
        this.temperatureThresold = temperatureThresold;
        this.pressureThresold = pressureThresold;
        this.windSpeedThresold = windSpeedThresold;
        this.windGustThresold = windGustThresold;
        this.cloudCoverThresold = cloudCoverThresold;
        this.visibilityThresold = visibilityThresold;
        this.precipitationIntensitySwitch = precipitationIntensitySwitch;
        this.precipitationProbabilitySwitch = precipitationProbabilitySwitch;
        this.temperatureSwitch = temperatureSwitch;
        this.pressureSwitch = pressureSwitch;
        this.windSpeedSwitch = windSpeedSwitch;
        this.windGustSwitch = windGustSwitch;
        this.cloudCoverSwitch = cloudCoverSwitch;
        this.visibilitySwitch = visibilitySwitch;
    }

    public Float getPrecipitationIntensityThresold() {
        return precipitationIntensityThresold;
    }

    public Float getPrecipitationProbabilityThresold() {
        return precipitationProbabilityThresold;
    }

    public Float getTemperatureThresold() {
        return temperatureThresold;
    }

    public Float getPressureThresold() {
        return pressureThresold;
    }

    public Float getWindSpeedThresold() {
        return windSpeedThresold;
    }

    public Float getWindGustThresold() {
        return windGustThresold;
    }

    public Float getCloudCoverThresold() {
        return cloudCoverThresold;
    }

    public Float getVisibilityThresold() {
        return visibilityThresold;
    }

    public Boolean getPrecipitationIntensitySwitch() {
        return precipitationIntensitySwitch;
    }

    public Boolean getPrecipitationProbabilitySwitch() {
        return precipitationProbabilitySwitch;
    }

    public Boolean getTemperatureSwitch() {
        return temperatureSwitch;
    }

    public Boolean getPressureSwitch() {
        return pressureSwitch;
    }

    public Boolean getWindSpeedSwitch() {
        return windSpeedSwitch;
    }

    public Boolean getWindGustSwitch() {
        return windGustSwitch;
    }

    public Boolean getCloudCoverSwitch() {
        return cloudCoverSwitch;
    }

    public Boolean getVisibilitySwitch() {
        return visibilitySwitch;
    }
}
