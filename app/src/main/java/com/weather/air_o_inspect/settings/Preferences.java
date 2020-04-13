package com.weather.air_o_inspect.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.weather.air_o_inspect.R;

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

    SharedPreferences sharedPref;
    //    SharedPreferences.Editor sharedPrefEditor;
    Context applicationContext;

    public static Preferences getPreferences(Context applicationContext) {
        if (preferences == null) {
            preferences = new Preferences();
            preferences.configSessionUtils(applicationContext);
        }
        return preferences;
    }

    public static Preferences getPreferences() {

        return preferences;
    }

    public void configSessionUtils(Context applicationContext) {
        this.applicationContext = applicationContext;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this.applicationContext);
//        sharedPrefEditor = sharedPref.edit();
    }
//
//    public void storeValueString(String key, String value) {
//        sharedPrefEditor.putString(key, value);
//        sharedPrefEditor.commit();
//    }

    public void fetchValueString() {
        setWindThresold(sharedPref.getInt("wind_seek", R.integer.wind_default));
        setWindGustThresold(sharedPref.getInt("wind_gust_seek", R.integer.wind_gust_default));
        setPrecipitationThresold(sharedPref.getInt("precipitation_seek", R.integer.precipitation_default));
        setPrecipitationProbabilityThresold(sharedPref.getInt("precipitation_probability_seek", R.integer.precipitation_probability_default));
        setTemperatureThresold(sharedPref.getInt("temperature_seek", R.integer.temperature_default));
        setCloudCoverThresold(sharedPref.getInt("cloud_cover_seek", R.integer.cloud_cover_default));
        setVisibilityThresold(sharedPref.getInt("visibility_seek", R.integer.visibility_default));
        Log.i("fetValueString", String.valueOf(getPreferences().getVisibilityThresold()));

    }

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
