package com.weather.air_o_inspect;


import android.app.Application;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MyApp extends Application implements LocationListener, Serializable {
    private static final int REQUEST_CODE = 15;
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static String longLat = "52.307108,10.530236";
    private final static String query = "units=si";
    private final static Long timeDelay = 20L; // Time in mins
    private final static String[] LABELS = {"Precipitation Intensity", "Precipitation Probability",
            "Temperature", "Pressure", "Wind Speed", "Wind Gust", "Cloud Cover", "Visibility"};
    private final static String[] COLUMNS = {"time", "precipIntensity", "precipProbability", "temperature", "pressure",
            "windSpeed", "windGust", "cloudCover", "visibility"};
    private final static String[] UNITS = {"mm", "Percent", "Celsius", "Pa", "m/s", "m/s", "Percent", "km"};
    private static Float[] COLUMNS_MAXVALUE = new Float[]{0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f};
    private final static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH", Locale.getDefault());
    private final static Float[] COLUMNS_THRESOLD = new Float[]{0.002f, 0.005f, 7f, 684f, 4f, 7f, 0.08f, 11f};
    private final static Boolean[] COLUMNS_SWITCH = new Boolean[]{true, true, true, true, true, true, true, true};

    private static final SimpleDateFormat simpleDateWithTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());


    private static MyApp instance;

    public static synchronized MyApp getInstance() {
        if (instance == null) {
            instance = new MyApp();
        }
        return instance;
    }

    public static String[] getUNITS() {
        return UNITS;
    }

    public static SimpleDateFormat getSimpleDateWithTimeFormat() {
        return simpleDateWithTimeFormat;
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public static Float[] getColumnsThresold() {
        return COLUMNS_THRESOLD;
    }

    public static Boolean[] getColumnsSwitch() {
        return COLUMNS_SWITCH;
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

    public static String[] getLABELS() {
        return LABELS;
    }

    public static int getREQUEST_CODE() {
        return REQUEST_CODE;
    }

    public static String[] getCOLUMNS() {
        return COLUMNS;
    }

    public static Float[] getColumnsMaxvalue() {
        return COLUMNS_MAXVALUE;
    }

    public static void setColumnsMaxvalue(Float[] columnsMaxvalue) {
        COLUMNS_MAXVALUE = columnsMaxvalue;
    }

    @Override
    public void onCreate() {
//        Log.d("MyApp: OnCreate: ", "Start");
        super.onCreate();

    }

    void fn_update(Location location) {
//        Log.d("MyApp: fn_update: ", "Start");
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
//        Log.i("MyApp:onLocChanged:", "Start");
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

    public static SimpleDateFormat getSimpleTimeFormat() {
        return simpleTimeFormat;
    }
}
