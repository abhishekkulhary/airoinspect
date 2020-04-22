package com.weather.air_o_inspect;


import android.app.Application;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MyApplication extends Application implements LocationListener, Serializable {
    private static final int REQUEST_CODE = 15;
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static final String SUNSHINE_COLUMN = "Sunshine";
    private static final String TEMPERATURE_COLUMN = "Temperature";
    private static final String WIND_SPEED_COLUMN = "Wind Speed";
    private static final String WIND_GUST_COLUMN = "Wind Gust";
    private static final String PRECIP_INTENSITY_COLUMN = "Precipitation Intensity";
    private static final String PRECIP_PROBABILITY_COLUMN = "Precipitation Probability";
    private final static List<String> COLUMNS = Arrays.asList(SUNSHINE_COLUMN, TEMPERATURE_COLUMN, WIND_SPEED_COLUMN, WIND_GUST_COLUMN,
            PRECIP_INTENSITY_COLUMN, PRECIP_PROBABILITY_COLUMN);
    private final static List<String> UNITS = Arrays.asList("%", "ºC", "m/s", "m/s", "mm", "%");
    private final static String query = "units=si";
    private final static Long timeDelay = 30L; // Time in mins
    private final static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH", Locale.getDefault());
    private static final SimpleDateFormat simpleDateWithTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
    private static final SimpleDateFormat simpleDateWithTimeInChart = new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault());
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    private static final SimpleDateFormat simpleTimeWithMinFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static String longLat = "52.307108,10.530236";
    private static MyApplication instance;

    public static synchronized MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    public static String getSunshineColumn() {
        return SUNSHINE_COLUMN;
    }

    public static String getTemperatureColumn() {
        return TEMPERATURE_COLUMN;
    }

    public static String getWindSpeedColumn() {
        return WIND_SPEED_COLUMN;
    }

    public static String getWindGustColumn() {
        return WIND_GUST_COLUMN;
    }

    public static String getPrecipIntensityColumn() {
        return PRECIP_INTENSITY_COLUMN;
    }

    public static String getPrecipProbabilityColumn() {
        return PRECIP_PROBABILITY_COLUMN;
    }

    public static List<String> getUNITS() {
        return UNITS;
    }

    public static SimpleDateFormat getSimpleDateWithTimeFormat() {
        return simpleDateWithTimeFormat;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public static SimpleDateFormat getSimpleDateWithTimeInChart() {
        return simpleDateWithTimeInChart;
    }

    public static SimpleDateFormat getSimpleTimeWithMinFormat() {
        return simpleTimeWithMinFormat;
    }

    public static String getLongLat() {
        return longLat;
    }

    public static String getQuery() {
        return query;
    }

    public static Long getTimeDelay() {
        return timeDelay;
    }

    public static int getREQUEST_CODE() {
        return REQUEST_CODE;
    }

    public static List<String> getCOLUMNS() {
        return COLUMNS;
    }

    public static SimpleDateFormat getSimpleTimeFormat() {
        return simpleTimeFormat;
    }

    @Override
    public void onCreate() {
//        Log.d("MyApplication: OnCreate: ", "Start");
        super.onCreate();

    }

    void fn_update(Location location) {
//        Log.d("MyApplication: fn_update: ", "Start");
        longLat = location.getLatitude() + "," + location.getLongitude();
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

    @Override
    public void onLocationChanged(Location location) {
//        Log.i("MyApplication:onLocChanged:", "Start");
        fn_update(location);
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
