package com.weather.air_o_inspect.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "preferences")
public class Preferences {

    @PrimaryKey
    private Integer id = 1;
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

    @Ignore
    public Preferences() {
        this.precipitationIntensityThresold = 0.002f;
        this.precipitationProbabilityThresold = 0.005f;
        this.temperatureThresold = 7f;
        this.pressureThresold = 684f;
        this.windSpeedThresold = 4f;
        this.windGustThresold = 7f;
        this.cloudCoverThresold = 0.08f;
        this.visibilityThresold = 11f;
        this.precipitationIntensitySwitch = true;
        this.precipitationProbabilitySwitch = true;
        this.temperatureSwitch = true;
        this.pressureSwitch = true;
        this.windSpeedSwitch = true;
        this.windGustSwitch = true;
        this.cloudCoverSwitch = true;
        this.visibilitySwitch = true;
    }

    public Preferences(@NonNull Float precipitationIntensityThresold, @NonNull Float precipitationProbabilityThresold,
                       @NonNull Float temperatureThresold, @NonNull Float pressureThresold,
                       @NonNull Float windSpeedThresold, @NonNull Float windGustThresold,
                       @NonNull Float cloudCoverThresold, @NonNull Float visibilityThresold, @NonNull Boolean precipitationIntensitySwitch,
                       @NonNull Boolean precipitationProbabilitySwitch, @NonNull Boolean temperatureSwitch, @NonNull Boolean pressureSwitch,
                       @NonNull Boolean windSpeedSwitch, @NonNull Boolean windGustSwitch, @NonNull Boolean cloudCoverSwitch,
                       @NonNull Boolean visibilitySwitch) {
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

    public void setPrecipitationIntensityThresold(Float precipitationIntensityThresold) {
        this.precipitationIntensityThresold = precipitationIntensityThresold;
    }

    public Float getPrecipitationProbabilityThresold() {
        return precipitationProbabilityThresold;
    }

    public void setPrecipitationProbabilityThresold(Float precipitationProbabilityThresold) {
        this.precipitationProbabilityThresold = precipitationProbabilityThresold;
    }

    public Float getTemperatureThresold() {
        return temperatureThresold;
    }

    public void setTemperatureThresold(Float temperatureThresold) {
        this.temperatureThresold = temperatureThresold;
    }

    public Float getPressureThresold() {
        return pressureThresold;
    }

    public void setPressureThresold(Float pressureThresold) {
        this.pressureThresold = pressureThresold;
    }

    public Float getWindSpeedThresold() {
        return windSpeedThresold;
    }

    public void setWindSpeedThresold(Float windSpeedThresold) {
        this.windSpeedThresold = windSpeedThresold;
    }

    public Float getWindGustThresold() {
        return windGustThresold;
    }

    public void setWindGustThresold(Float windGustThresold) {
        this.windGustThresold = windGustThresold;
    }

    public Float getCloudCoverThresold() {
        return cloudCoverThresold;
    }

    public void setCloudCoverThresold(Float cloudCoverThresold) {
        this.cloudCoverThresold = cloudCoverThresold;
    }

    public Float getVisibilityThresold() {
        return visibilityThresold;
    }

    public void setVisibilityThresold(Float visibilityThresold) {
        this.visibilityThresold = visibilityThresold;
    }

    public Boolean getPrecipitationIntensitySwitch() {
        return precipitationIntensitySwitch;
    }

    public void setPrecipitationIntensitySwitch(Boolean precipitationIntensitySwitch) {
        this.precipitationIntensitySwitch = precipitationIntensitySwitch;
    }

    public Boolean getPrecipitationProbabilitySwitch() {
        return precipitationProbabilitySwitch;
    }

    public void setPrecipitationProbabilitySwitch(Boolean precipitationProbabilitySwitch) {
        this.precipitationProbabilitySwitch = precipitationProbabilitySwitch;
    }

    public Boolean getTemperatureSwitch() {
        return temperatureSwitch;
    }

    public void setTemperatureSwitch(Boolean temperatureSwitch) {
        this.temperatureSwitch = temperatureSwitch;
    }

    public Boolean getPressureSwitch() {
        return pressureSwitch;
    }

    public void setPressureSwitch(Boolean pressureSwitch) {
        this.pressureSwitch = pressureSwitch;
    }

    public Boolean getWindSpeedSwitch() {
        return windSpeedSwitch;
    }

    public void setWindSpeedSwitch(Boolean windSpeedSwitch) {
        this.windSpeedSwitch = windSpeedSwitch;
    }

    public Boolean getWindGustSwitch() {
        return windGustSwitch;
    }

    public void setWindGustSwitch(Boolean windGustSwitch) {
        this.windGustSwitch = windGustSwitch;
    }

    public Boolean getCloudCoverSwitch() {
        return cloudCoverSwitch;
    }

    public void setCloudCoverSwitch(Boolean cloudCoverSwitch) {
        this.cloudCoverSwitch = cloudCoverSwitch;
    }

    public Boolean getVisibilitySwitch() {
        return visibilitySwitch;
    }

    public void setVisibilitySwitch(Boolean visibilitySwitch) {
        this.visibilitySwitch = visibilitySwitch;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }
}
