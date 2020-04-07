package com.weather.air_o_inspect;


import android.app.Application;
import android.content.res.Configuration;

public class MyApp extends Application {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static String[] filename = new String[]{"forecast1.csv", "forecast2.csv"};
    private static String longLat = "52.307108,10.530236";
    private static String query = "units=si";
    private static Long timeDelay = 5L; // Time in mins

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
}
