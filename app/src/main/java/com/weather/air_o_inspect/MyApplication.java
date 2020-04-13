package com.weather.air_o_inspect;


import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.weather.air_o_inspect.settings.Preferences;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyApplication extends Application implements LocationListener, Serializable {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static final String[] filename = new String[]{"currentforecast.csv", "forecast.csv"};
    private static String longLat = "52.307108,10.530236";
    private static String query = "units=si";
    private static Long timeDelay = 20L; // Time in mins
    private final Integer REQUEST_CODE = 15;

    private final String xColumn = "time";
    private final String[] COLUMNS = {"precipIntensity", "precipProbability", "temperature", "pressure",
            "windSpeed", "windGust", "cloudCover", "visibility"};
    private static Float[] COLUMNS_MAXVALUE = new Float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};

    private final String[] TAB_SUBTEXT = {"Today", "Tomorrow", "Day After"};
    private final String[] LABELS = {"Precipitation Intensity", "Precipitation Probability", "Temperature", "Pressure", "Wind Speed", "Wind Gust", "Cloud Cover", "Visibility"};

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH", Locale.getDefault());
    private final SimpleDateFormat simpleTimesFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault());

    private MyApplication myApplication = null;

    SharedPreferences sharedPref;

    @Override
    public void onCreate() {
//        Log.d("MyApp: OnCreate: ", "Start");
        super.onCreate();
        setTheme(R.style.AppTheme);
        Preferences.getPreferences(getApplicationContext());

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        fetchValueString();
//        Log.d("MyApp: OnCreate: ", "End");
    }


    public void fetchValueString() {
        Preferences.getPreferences().setWindThresold(sharedPref.getInt("wind_seek", (getResources().getInteger(R.integer.wind_default))));
        Preferences.getPreferences().setWindGustThresold(sharedPref.getInt("wind_gust_seek", (getResources().getInteger(R.integer.wind_gust_default))));
        Preferences.getPreferences().setPrecipitationThresold(sharedPref.getInt("precipitation_seek", (getResources().getInteger(R.integer.precipitation_default))));
        Preferences.getPreferences().setPrecipitationProbabilityThresold(sharedPref.getInt("precipitation_probability_seek", (getResources().getInteger(R.integer.precipitation_probability_default))));
        Preferences.getPreferences().setTemperatureThresold(sharedPref.getInt("temperature_seek",(getResources().getInteger(R.integer.temperature_default))));
        Preferences.getPreferences().setCloudCoverThresold(sharedPref.getInt("cloud_cover_seek",(getResources().getInteger(R.integer.cloud_cover_default))));
        Preferences.getPreferences().setVisibilityThresold(sharedPref.getInt("visibility_seek", (getResources().getInteger(R.integer.visibility_default))));

    }

    void fn_update(Location location) {
//        Log.d("MyApp: fn_update: ", "Start");
        longLat = location.getLatitude() + "," + location.getLongitude();
//        Log.d("MyApp: fn_update: ", "End");
    }

    public String getxColumn() {
        return xColumn;
    }

    public String[] getFilename() {
        return filename;
    }

    public String getLongLat() {
        return longLat;
    }

    public void setLongLat(String longLat) {
        MyApplication.longLat = longLat;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        MyApplication.query = query;
    }

    public Long getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(Long timeDelay) {
        MyApplication.timeDelay = timeDelay;
    }

    public String[] getCOLUMNS() {
        return COLUMNS;
    }

    public String[] getLABELS() {
        return LABELS;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public SimpleDateFormat getSimpleTimeFormat() {
        return simpleTimeFormat;
    }

    public SimpleDateFormat getSimpleTimesFormat() {
        return simpleTimesFormat;
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public Integer getREQUEST_CODE() {
        return REQUEST_CODE;
    }

    @Override
    public void onLocationChanged(Location location) {
//        Log.i("MyApp:onLocChanged:", "Start");
        fn_update(location);
//        Log.i("MyApp:onLocChanged:", "End");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public String[] getTAB_SUBTEXT() {
        return TAB_SUBTEXT;
    }

    public Float[] getCOLUMNS_MAXVALUE() {
        return COLUMNS_MAXVALUE;
    }

    public void setCOLUMNS_MAXVALUE(Float[] COLUMNS_MAXVALUE) {
        MyApplication.COLUMNS_MAXVALUE = COLUMNS_MAXVALUE;
    }
}
