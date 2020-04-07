package com.weather.air_o_inspect.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.weather.air_o_inspect.MyApp;

public class LoadWeatherService extends Service implements LocationListener {

    Context context;

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    MyApp myApp = new MyApp();

    public LoadWeatherService(Context applicationContext) {
        super();
        context = applicationContext;
        Log.i("HERE", "here service created!");
    }

    public LoadWeatherService() {
    }

    Handler handler = new Handler();
    public Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1000 * 60 * myApp.getTimeDelay()); // schedule next wake up every X mins

            fn_getlocation();
            if (location != null) {
                myApp.setLongLat(location.getLatitude() + "," + location.getLongitude());
            }

            AsyncBackgroundTask asyncBackgroundTask = new AsyncBackgroundTask(myApp.getLongLat(), myApp.getQuery(), myApp.getFilename(), getApplicationContext());
            asyncBackgroundTask.execute();
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
        myApp.setLongLat(location.getLatitude() + "," + location.getLongitude());
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
