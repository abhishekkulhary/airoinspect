package com.weather.air_o_inspect.settings;

import androidx.preference.Preference;

public class Preferences {

    private int windSeek;
    private int windGustSeek;
    private float precipitationSeek;
    private static Preferences preferences = null;

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
