package com.weather.air_o_inspect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.preference.SeekBarPreference;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.weather.air_o_inspect.CurrentStatus.CurrentStatusData;
import com.weather.air_o_inspect.Utils.UtilsWeatherDataRead;
import com.weather.air_o_inspect.service.LoadWeatherService;
import com.weather.air_o_inspect.settings.Preferences;
import com.weather.air_o_inspect.settings.SettingsFragment;
import com.weather.air_o_inspect.ui.main.SectionsPagerAdapter;

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

    private ViewPager viewPager;
    private TabLayout tabs;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static final String mBroadcastRepeatAction = "com.weather.air_o_inspect.repeat";
    private Integer count = 0;

    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private IntentFilter mIntentFilter;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private Map<String, Map<String, ArrayList<Float>>> weatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("MainActivity: onCreate:", "Start");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        utilsWeatherDataRead = new UtilsWeatherDataRead(getApplicationContext());

        mIntentFilter = new IntentFilter();
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

        fn_getlocation();

        Utils.init(this);

        Intent intent = new Intent(getApplicationContext(), LoadWeatherService.class);
        intent.putExtra("isRepeat", true);
        startService(intent);

        inflateUI();
    }

    private void inflateUI() {
        Log.i("MainActivity:inflateUI:", "Start");

        Observable<Map<String, List<String>>> observable = Observable.defer(new Callable<Observable<Map<String, List<String>>>>() {
            @Override
            public Observable<Map<String, List<String>>> call() throws Exception {
                Log.i("In Observable", "Hello done till here");
                Map<String, Map<String, ArrayList<Float>>> currentWeatherData = utilsWeatherDataRead.readWeatherData(myApp.getFilename()[0]);
                Map<String, List<String>> currentWeatherCondition = utilsWeatherDataRead.getCurrentWeatherConditions(currentWeatherData);

                Log.i("In Observable2", "Hello done till here");

                weatherData = utilsWeatherDataRead.readWeatherData(myApp.getFilename()[1]);

                return Observable.just(currentWeatherCondition);
            }
        });

        DisposableObserver<Map<String, List<String>>> disposableObserver = new DisposableObserver<Map<String, List<String>>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onNext(Map<String, List<String>> currentWeatherCondition) {
                sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), weatherData.keySet().size(),
                        getSupportFragmentManager(), utilsWeatherDataRead, myApp, weatherData);
                viewPager.setAdapter(sectionsPagerAdapter);
                tabs.setupWithViewPager(viewPager);

                for (int i = 0; i < tabs.getTabCount(); i++) {
                    TabLayout.Tab tab = tabs.getTabAt(i);
                    tab.setCustomView(sectionsPagerAdapter.getTabView(i));
                }
                tabs.addOnTabSelectedListener(new TabListner());
                // TODO
// TODO 1. Add Units for each item
                // TODO
                if (currentWeatherCondition != null && !currentWeatherCondition.isEmpty()) {
                    Long currentTime = Long.parseLong(currentWeatherCondition.get("values")
                            .get(currentWeatherCondition.get("titles").indexOf("time")));
                    Log.i("currentstatusdata", "inside if cond");
                    if ((SystemClock.currentThreadTimeMillis() - currentTime * 1000) > 43200000) {
                        Intent stopIntent = new Intent(MainActivity.this,
                                LoadWeatherService.class);
                        stopService(stopIntent);
                        Intent intent = new Intent(getApplicationContext(), LoadWeatherService.class);
                        intent.putExtra("isRepeat", false);
                        startService(intent);
                        return;
                    }
                    CurrentStatusData currentStatusData = new CurrentStatusData();
                    currentStatusData.populatateCurrentStatus(currentWeatherCondition, myApp);
//                    RecyclerView currentStatusView = findViewById(R.id.current_status_view);
//                    CurrentStatusViewAdapter currentStatusViewAdapter = new CurrentStatusViewAdapter(currentStatusData);
//                    currentStatusView.setAdapter(currentStatusViewAdapter);
                    currentFlyStatus = findViewById(R.id.current_fly_status);
                    currentTemperature = findViewById(R.id.current_temperature);
                    currentRainStatus = findViewById(R.id.current_rain_status);
                    currentWind = findViewById(R.id.current_wind);
                    currentVisibility = findViewById(R.id.current_visibility);
                    currentTimePlace = findViewById(R.id.current_time_place);
//                    currentFlyStatus.setText(currentStatusData.getCurrent_fly_status());
                    currentTemperature.setText(currentStatusData.getCurrent_temperature());
                    currentRainStatus.setText(currentStatusData.getCurrent_rain_status());
                    currentWind.setText(currentStatusData.getCurrent_wind());
                    currentVisibility.setText(currentStatusData.getCurrent_visibility());
                    currentTimePlace.setText(currentStatusData.getCurrent_time_place());

                }
                Log.i("Preferences", String.valueOf(Preferences.getPreferences().getWindGustSeek()));


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };

        disposables.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(disposableObserver));
    }

    private final class TabListner implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            View view = tab.getCustomView();
            if (view instanceof LinearLayout) {
                for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
                    ((AppCompatTextView) ((LinearLayout) view).getChildAt(i)).setTypeface(Typeface.DEFAULT_BOLD);
                    ((AppCompatTextView) ((LinearLayout) view).getChildAt(i)).setTextAppearance(getApplicationContext(),
                            android.R.style.TextAppearance_DeviceDefault_Widget_TabWidget);

                }
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            View view = tab.getCustomView();
            if (view instanceof LinearLayout) {
                for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {
                    ((AppCompatTextView) ((LinearLayout) view).getChildAt(i)).setTypeface(Typeface.DEFAULT);
                    ((AppCompatTextView) ((LinearLayout) view).getChildAt(i)).setTextAppearance(getApplicationContext(),
                            android.R.style.TextAppearance_DeviceDefault_Widget_TabWidget);
                }
            }
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Objects.equals(intent.getAction(), mBroadcastRepeatAction)) {
                Log.i("MainActivity:mReceiver:", "Else If Start");
                disposables.clear();
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
        Intent stopIntent = new Intent(MainActivity.this,
                LoadWeatherService.class);
        stopService(stopIntent);
        Intent intent = new Intent(getApplicationContext(), LoadWeatherService.class);
        intent.putExtra("isRepeat", false);
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
