package com.weather.air_o_inspect.settings;

public class Preferences {

    private int windSeek;
    private int windGustSeek;
    private float precipitationSeek;
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

    public int getWindSeek() {
        return windSeek;
    }

    public void setWindSeek(int windSeek) {
        this.windSeek = windSeek;
    }

    public int getWindGustSeek() {
        return windGustSeek;
    }

    public void setWindGustSeek(int windGustSeek) {
        this.windGustSeek = windGustSeek;
    }

    public float getPrecipitationSeek() {
        return precipitationSeek;
    }

    public void setPrecipitationSeek(float precipitationSeek) {
        this.precipitationSeek = precipitationSeek;
    }

}
