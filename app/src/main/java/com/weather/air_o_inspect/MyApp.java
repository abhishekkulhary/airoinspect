package com.weather.air_o_inspect;


import android.app.Application;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyApp extends Application implements LocationListener, Serializable {
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
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private final SimpleDateFormat simpleTimesFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault());

    @Override
    public void onCreate() {
//        Log.d("MyApp: OnCreate: ", "Start");
        super.onCreate();
//        Log.d("MyApp: OnCreate: ", "End");
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
        MyApp.COLUMNS_MAXVALUE = COLUMNS_MAXVALUE;
    }
}
