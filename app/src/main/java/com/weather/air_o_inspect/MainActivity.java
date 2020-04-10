package com.weather.air_o_inspect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.weather.air_o_inspect.datareadutil.UtilsWeatherDataRead;
import com.weather.air_o_inspect.service.LoadWeatherNoLimitService;
import com.weather.air_o_inspect.service.LoadWeatherService;
import com.weather.air_o_inspect.settings.SettingsFragment;
import com.weather.air_o_inspect.ui.main.SectionsPagerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private final MyApp myApp = new MyApp();
    private UtilsWeatherDataRead utilsWeatherDataRead;

    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;
    private ViewPager viewPager;
    private TabLayout tabs;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static final String mBroadcastOneTimeAction = "com.weather.air_o_inspect.onetime";
    public static final String mBroadcastRepeatAction = "com.weather.air_o_inspect.repeat";
    private Integer count = 0;


    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private IntentFilter mIntentFilter;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity: onCreate:", "Start");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.weather_app);

        currentFlyStatus = findViewById(R.id.current_fly_status);
        currentTemperature = findViewById(R.id.current_temperature);
        currentRainStatus = findViewById(R.id.current_rain_status);
        currentWind = findViewById(R.id.current_wind);
        currentVisibility = findViewById(R.id.current_visibility);
        currentTimePlace = findViewById(R.id.current_time_place);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastOneTimeAction);
        mIntentFilter.addAction(mBroadcastRepeatAction);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        myApp.getREQUEST_CODE());
        }
        utilsWeatherDataRead = new UtilsWeatherDataRead(this);
        sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), myApp.getFilename().length,
                getSupportFragmentManager(), utilsWeatherDataRead, myApp);

        fn_getlocation();

        Intent startIntent = new Intent(MainActivity.this,
                LoadWeatherNoLimitService.class);
        startService(startIntent);

        inflateUI();

        Utils.init(this);
    }

    private void inflateUI() {
        Log.i("MainActivity:inflateUI:", "Start");
        ArrayList<String[]> weatherData = utilsWeatherDataRead.readWeatherData("Currentforecast.csv");
        Map<String, List<String>> currentWeatherCondition = utilsWeatherDataRead.getCurrentWeatherConditions(weatherData);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        // TODO
// TODO 1. Add Units for each item
        // TODO
        if (currentWeatherCondition != null && !currentWeatherCondition.isEmpty()) {
            currentTimePlace.setText(
                    myApp.getSimpleDateFormat().format(Long.parseLong(currentWeatherCondition.get("values")
                            .get(currentWeatherCondition.get("titles").indexOf("time"))))
                            + " " + myApp.getSimpleTimesFormat().format(Long.parseLong(
                            currentWeatherCondition.get("values")
                                    .get(currentWeatherCondition.get("titles").indexOf("time"))) * 1000));
            //currentFlyStatus.setText(currentWeatherCondition.get("values")
            // .get(currentWeatherCondition.get("titles").indexOf("time")));
            currentRainStatus.setText("Precipitation: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("precipIntensity")));
            currentTemperature.setText("Temperature: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("temperature")));
            currentVisibility.setText("Sun or Visibility: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("visibility")));
            currentWind.setText("Wind Speed: " + currentWeatherCondition.get("values")
                    .get(currentWeatherCondition.get("titles").indexOf("windSpeed")));
        }

//        Observable<Map<String, List<String>>> observable = Observable.defer(new Callable<Observable<Map<String, List<String>>>>() {
//            @Override
//            public Observable<Map<String, List<String>>> call() throws Exception {
//                return Observable.just(currentWeatherCondition);
//            }
//        });
//
//        DisposableObserver<Map<String, List<String>>> disposableObserver = new DisposableObserver<Map<String, List<String>>>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onNext(Map<String, List<String>> currentWeatherCondition) {
//
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        };
//
//        disposables.add(
//                observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(disposableObserver));
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Objects.equals(intent.getAction(), mBroadcastOneTimeAction)) {
                Log.i("MainActivity:mReceiver:", "If Start");
                inflateUI();
                Intent stopIntent = new Intent(MainActivity.this,
                        LoadWeatherService.class);
                stopService(stopIntent);
            } else if (Objects.equals(intent.getAction(), mBroadcastRepeatAction)) {
                Log.i("MainActivity:mReceiver:", "Else If Start");
                inflateUI();
            }
        }
    };


    public void fn_getlocation() {
        Log.i("MainActivity:fn_getLoc:", "Start");
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        assert locationManager != null;
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, myApp);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {

                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        myApp.fn_update(location);
                    }
                }

            }

            if (isGPSEnable) {
                location = null;
                assert locationManager != null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, myApp);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        Log.e("latitude", location.getLatitude() + "");
                        Log.e("longitude", location.getLongitude() + "");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        myApp.fn_update(location);
                    }
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("MainActivity:createMen:", "Start");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i("MainActivity:", "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("MainActivity:", "onRequestPermissionResult");
        if (requestCode == myApp.getREQUEST_CODE()) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Log.d("onRequestPermissionsRe:", "Permission Not Granted");
                    return;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        fn_getlocation();
        Intent intent = new Intent(getApplicationContext(), LoadWeatherService.class);
        startService(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
