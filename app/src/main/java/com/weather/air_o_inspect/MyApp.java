package com.weather.air_o_inspect;


import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.weather.air_o_inspect.service.LoadWeatherService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

@SuppressWarnings("ALL")
public class MyApp extends Application implements LocationListener, Serializable {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static final String[] filename = new String[]{"forecast1.csv", "forecast2.csv"};
    private static String longLat = "52.307108,10.530236";
    private static String query = "units=si";
    private static Long timeDelay = 10L; // Time in mins
    private final Integer REQUEST_CODE = 15;
    private final String xColumn = "time";
    private final String[][] COLUMNS = {{"precipIntensity"}, {"precipProbability"}, {"temperature"}, {"pressure"},
            {"windSpeed"}, {"windGust"}, {"cloudCover"}, {"visibility"}};
    private final String[] LABELS = {"Precipitation Intensity", "Precipitation Probability", "Temperature", "Pressure", "Wind Speed", "Wind Gust", "Cloud Cover", "Visibility"};
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.US);
    private final SimpleDateFormat simpleTimesFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    @Override
    public void onCreate() {
        Log.i("MyApp: OnCreate: ", "Start");
        super.onCreate();
        Log.i("MyApp: OnCreate: ", "End");
    }

    void fn_update(Location location) {
        Log.i("MyApp: fn_update: ", "Start");
        longLat = location.getLatitude() + "," + location.getLongitude();
        Log.i("MyApp: fn_update: ", "End");
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
        MyApp.longLat = longLat;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        MyApp.query = query;
    }

    public Long getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(Long timeDelay) {
        MyApp.timeDelay = timeDelay;
    }

    public String[][] getCOLUMNS() {
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
        Log.i("MyApp: onLocationChanged: ", "Start");
        fn_update(location);
        Log.i("MyApp: onLocationChanged: ", "End");
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
}
