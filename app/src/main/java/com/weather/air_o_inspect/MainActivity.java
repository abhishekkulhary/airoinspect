package com.weather.air_o_inspect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.weather.air_o_inspect.datareadutil.UtilsWeatherDataRead;
import com.weather.air_o_inspect.service.LoadWeatherService;
import com.weather.air_o_inspect.settings.SettingsFragment;
import com.weather.air_o_inspect.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity{

    private final MyApp myApp = new MyApp();
    private UtilsWeatherDataRead utilsWeatherDataRead;

    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;
    private final CompositeDisposable disposables = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, myApp.getREQUEST_CODE());
        }

        utilsWeatherDataRead  = new UtilsWeatherDataRead(this);

        startService(new Intent(getApplicationContext(), LoadWeatherService.class));

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.weather_app);

        Utils.init(this);

        currentFlyStatus = findViewById(R.id.current_fly_status);
        currentTemperature = findViewById(R.id.current_temperature);
        currentRainStatus = findViewById(R.id.current_rain_status);
        currentWind = findViewById(R.id.current_wind);
        currentVisibility = findViewById(R.id.current_visibility);
        currentTimePlace = findViewById(R.id.current_time_place);

        final Handler handler = new Handler();
        Runnable periodicUpdate = new Runnable() {
            @Override
            public void run() {

                SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), getSupportFragmentManager(), utilsWeatherDataRead);
                ViewPager viewPager = findViewById(R.id.view_pager);
                viewPager.setAdapter(sectionsPagerAdapter);
                TabLayout tabs = findViewById(R.id.tabs);
                tabs.setupWithViewPager(viewPager);

                Observable<Map<String, List<String>>> observable = Observable.defer(new Callable<Observable<Map<String, List<String>>>>() {
                    @Override
                    public Observable<Map<String, List<String>>> call() throws Exception {
                        ArrayList<String[]> weatherData = utilsWeatherDataRead.readWeatherData("Currentforecast.csv");
                        Map<String, List<String>> currentWeatherCondition = utilsWeatherDataRead.getCurrentWeatherConditions(weatherData);
                        return Observable.just(currentWeatherCondition);
                    }
                });

                DisposableObserver<Map<String, List<String>>> disposableObserver = new DisposableObserver<Map<String, List<String>>>() {
                    @Override
                    public void onNext(Map<String, List<String>> currentWeatherCondition) {
                        // TODO
// TODO 1. Add Units for each item
                        // TODO
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
        };

        handler.postDelayed(periodicUpdate, 500); // 1 Second delay in updating the Main UI.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
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
