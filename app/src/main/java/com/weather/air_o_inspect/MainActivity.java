package com.weather.air_o_inspect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.tabs.TabLayout;
import com.weather.air_o_inspect.Entities.CurrentStatus;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.Viewmodel.WeatherViewModel;
import com.weather.air_o_inspect.Service.LoadWeatherService;
import com.weather.air_o_inspect.Settings.SettingsFragment;
import com.weather.air_o_inspect.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabs;

    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;

    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currentFlyStatus = findViewById(R.id.current_fly_status);
        currentTemperature = findViewById(R.id.current_temperature);
        currentRainStatus = findViewById(R.id.current_rain_status);
        currentWind = findViewById(R.id.current_wind);
        currentVisibility = findViewById(R.id.current_visibility);
        currentTimePlace = findViewById(R.id.current_time_place);

        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.view_pager);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MyApp.getREQUEST_CODE());
        }



        Utils.init(this);

        Intent intent = new Intent(getApplicationContext(), LoadWeatherService.class);
        startService(intent);

        final WeatherViewModel weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(WeatherViewModel.class);
        weatherViewModel.getAllWeatherUpdate().observe(MainActivity.this, new Observer<List<WeatherUpdate>>() {
            @Override
            public void onChanged(List<WeatherUpdate> weatherUpdates) {
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                List<String> allDistinctDates = new ArrayList<>();
                for (WeatherUpdate weatherUpdate : weatherUpdates) {
                    if (!allDistinctDates.contains(weatherUpdate.getCurrentTime()))
                        allDistinctDates.add(weatherUpdate.getCurrentTime());
                }
                Collections.sort(allDistinctDates);
                System.out.println(allDistinctDates);

                sectionsPagerAdapter = new SectionsPagerAdapter(getApplicationContext(), allDistinctDates.size(),
                        getSupportFragmentManager());
                sectionsPagerAdapter.setTAB_TITLES(allDistinctDates);
                sectionsPagerAdapter.setWeatherUpdates(weatherUpdates);
                viewPager.setAdapter(sectionsPagerAdapter);
                tabs.setupWithViewPager(viewPager);

                for (int i = 0; i < tabs.getTabCount(); i++) {
                    TabLayout.Tab tab = tabs.getTabAt(i);
                    assert tab != null;
                    tab.setCustomView(sectionsPagerAdapter.getTabView(i));
                }
                tabs.addOnTabSelectedListener(new TabListner());
            }
        });

        weatherViewModel.getCurrentStatusLiveData().observe(MainActivity.this, new Observer<List<CurrentStatus>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<CurrentStatus> currentStatus) {
                if (!currentStatus.isEmpty()) {

                    final CurrentStatus currentStatus1 = currentStatus.get(0);
                    currentTemperature.setText("" + currentStatus1.getTemperature());
                    currentRainStatus.setText("" + currentStatus1.getPrecipProbability());
                    currentWind.setText("" + currentStatus1.getWindSpeed());
                    currentVisibility.setText("" + currentStatus1.getVisibility());
                    currentTimePlace.setText("" + currentStatus1.getCurrentTime());

                    weatherViewModel.getPreferencesLiveData().observe(MainActivity.this, new Observer<List<Preferences>>() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onChanged(List<Preferences> preferences) {

                            if (!preferences.isEmpty()) {

                                Preferences preferences1 = preferences.get(0);

                                Boolean[] checks = {preferences1.getPrecipitationIntensitySwitch(), preferences1.getPrecipitationProbabilitySwitch(),
                                        preferences1.getTemperatureSwitch(), preferences1.getPressureSwitch(), preferences1.getWindSpeedSwitch(),
                                        preferences1.getWindGustSwitch(), preferences1.getCloudCoverSwitch(), preferences1.getVisibilitySwitch()};

                                Boolean[] thresoldChecks = {currentStatus1.getPrecipIntensity() < preferences1.getPrecipitationIntensityThresold(),
                                        currentStatus1.getPrecipProbability() < preferences1.getPrecipitationProbabilityThresold(),
                                        currentStatus1.getTemperature() < preferences1.getTemperatureThresold(),
                                        currentStatus1.getPressure() < preferences1.getPressureThresold(),
                                        currentStatus1.getWindSpeed() < preferences1.getWindSpeedThresold(),
                                        currentStatus1.getWindGust() < preferences1.getWindGustThresold(),
                                        currentStatus1.getCloudCover() < preferences1.getCloudCoverThresold(),
                                        currentStatus1.getVisibility() < preferences1.getVisibilityThresold()};
                                boolean finalDecision = true;
                                int i = 0;
                                for (Boolean check : checks) {

                                    if (check) {

                                        if (!thresoldChecks[i]) {

                                            finalDecision = false;
                                            break;
                                        }

                                    }
                                    i++;
                                }
                                int green = Color.rgb(110, 190, 102);
                                int red = Color.rgb(211, 74, 88);
                                if (finalDecision) {
                                    currentFlyStatus.setTextColor(green);
                                } else {

                                    currentFlyStatus.setTextColor(red);
                                }
                            }

                        }
                    });
                }
            }
        });

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
        if (requestCode == MyApp.getREQUEST_CODE()) {
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
}
