package com.weather.air_o_inspect;


import android.app.Application;
import android.content.res.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyApp extends Application {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static String[] filename = new String[]{"forecast1.csv", "forecast2.csv"};
    private static String longLat = "52.307108,10.530236";
    private static String query = "units=si";
    private static Long timeDelay = 5L; // Time in mins
    private final Integer REQUEST_CODE = 15;

    private final String[][] COLUMNS = {{"precipIntensity"}, {"precipProbability"}, {"temperature"}, {"pressure"},
            {"windSpeed"}, {"windGust"}, {"cloudCover"}, {"visibility"}};
    private final String[] LABELS = {"Precipitation Intensity", "Precipitation Probability", "Temperature", "Pressure", "Wind Speed", "Wind Gust", "Cloud Cover", "Visibility"};
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.US);
    private final SimpleDateFormat simpleTimesFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    @Override
    public void onCreate() {
        super.onCreate();


    }

    public String[] getFilename() {
        return filename;
    }

    public void setFilename(String[] filename) {
        MyApp.filename = filename;
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
    public void onConfigurationChanged(Configuration newConfig) {
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
}
