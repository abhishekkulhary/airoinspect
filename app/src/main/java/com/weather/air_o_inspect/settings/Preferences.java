package com.weather.air_o_inspect.settings;

public class Preferences {

    private int windThresold;
    private int windGustThresold;
    private float precipitationThresold;
    private int precipitationProbabilityThresold;
    private int temperatureThresold;
    private int pressureThresold;
    private int cloudCoverThresold;
    private int visibilityThresold;
    private boolean windSwitch;
    private boolean windGustSwitch;
    private boolean precipitationSwitch;
    private boolean sunshineSwitch;

    private static Preferences preferences = null;

    public boolean isWindSwitch() {
        return windSwitch;
    }

    public void setWindSwitch(boolean windSwitch) {
        this.windSwitch = windSwitch;
    }

    public boolean isWindGustSwitch() {
        return windGustSwitch;
    }

    public void setWindGustSwitch(boolean windGustSwitch) {
        this.windGustSwitch = windGustSwitch;
    }

    public boolean isPrecipitationSwitch() {
        return precipitationSwitch;
    }

    public void setPrecipitationSwitch(boolean precipitationSwitch) {
        this.precipitationSwitch = precipitationSwitch;
    }

    public boolean isSunshineSwitch() {
        return sunshineSwitch;
    }

    public void setSunshineSwitch(boolean sunshineSwitch) {
        this.sunshineSwitch = sunshineSwitch;
    }

    private Preferences() {

    }

    public static Preferences getPreferences() {
        if (preferences == null) {
            preferences = new Preferences();
        }
        return preferences;
    }

    public int getWindThresold() {
        return windThresold;
    }

    public void setWindThresold(int windThresold) {
        this.windThresold = windThresold;
    }

    public int getWindGustThresold() {
        return windGustThresold;
    }

    public void setWindGustThresold(int windGustThresold) {
        this.windGustThresold = windGustThresold;
    }

    public float getPrecipitationThresold() {
        return precipitationThresold;
    }

    public void setPrecipitationThresold(float precipitationThresold) {
        this.precipitationThresold = precipitationThresold;
    }

    public int getPrecipitationProbabilityThresold() {
        return precipitationProbabilityThresold;
    }

    public void setPrecipitationProbabilityThresold(int precipitationProbabilityThresold) {
        this.precipitationProbabilityThresold = precipitationProbabilityThresold;
    }

    public int getTemperatureThresold() {
        return temperatureThresold;
    }

    public void setTemperatureThresold(int temperatureThresold) {
        this.temperatureThresold = temperatureThresold;
    }

    public int getPressureThresold() {
        return pressureThresold;
    }

    public void setPressureThresold(int pressureThresold) {
        this.pressureThresold = pressureThresold;
    }

    public int getCloudCoverThresold() {
        return cloudCoverThresold;
    }

    public void setCloudCoverThresold(int cloudCoverThresold) {
        this.cloudCoverThresold = cloudCoverThresold;
    }

    public int getVisibilityThresold() {
        return visibilityThresold;
    }

    public void setVisibilityThresold(int visibilityThresold) {
        this.visibilityThresold = visibilityThresold;
    }
}
