package com.weather.air_o_inspect;


import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyApp extends Application implements LocationListener {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    private static String[] filename = new String[]{"forecast1.csv", "forecast2.csv"};
    private static String longLat = "52.307108,10.530236";
    private static String query = "units=si";
    private static Long timeDelay = 5L; // Time in mins
    private final Integer REQUEST_CODE = 15;
    private final String xColumn = "time";
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;

    private final String[][] COLUMNS = {{"precipIntensity"}, {"precipProbability"}, {"temperature"}, {"pressure"},
            {"windSpeed"}, {"windGust"}, {"cloudCover"}, {"visibility"}};
    private final String[] LABELS = {"Precipitation Intensity", "Precipitation Probability", "Temperature", "Pressure", "Wind Speed", "Wind Gust", "Cloud Cover", "Visibility"};
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private final SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.US);
    private final SimpleDateFormat simpleTimesFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    @Override
    public void onCreate() {
        super.onCreate();
        fn_getlocation();
        if (location != null) {
            longLat = location.getLatitude() + "," + location.getLongitude();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        fn_getlocation();
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

    public void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){

                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }

            }

            if (isGPSEnable){
                location = null;
                assert locationManager != null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }
            }
        }

    }

    private void fn_update(Location location){
        longLat = location.getLatitude() + "," + location.getLongitude();
    }

    public String getxColumn() {
        return xColumn;
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
