package com.weather.air_o_inspect.settings;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.weather.air_o_inspect.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {


    private SharedPreferences sharedPreferences;
    SeekBarPreference windSeekBar;
    SeekBarPreference windGustSeekBar;
    SeekBarPreference precipitationSeekBar;
    SeekBarPreference precipitationProbabilitySeekBar;
    SeekBarPreference temperatureSeekBar;
    SeekBarPreference cloudCoverSeekBar;
    SeekBarPreference visibilitySeekBar;

    SwitchPreferenceCompat windSwitch;
    SwitchPreferenceCompat windGustSwitch;
    SwitchPreferenceCompat sunshineSwitch;
    SwitchPreferenceCompat precipitationSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        windSeekBar = findPreference("wind_seek");
        windGustSeekBar = findPreference("wind_gust_seek");
        precipitationSeekBar = findPreference("precipitation_seek");
        precipitationProbabilitySeekBar = findPreference("precipitation_probability_seek");
        temperatureSeekBar = findPreference("temperature_seek");
        cloudCoverSeekBar = findPreference("cloud_cover_seek");
        visibilitySeekBar = findPreference("visibility_seek");


        Preferences.getPreferences().setWindThresold(windSeekBar.getValue());
        Preferences.getPreferences().setWindGustThresold(windGustSeekBar.getValue());
        Preferences.getPreferences().setPrecipitationThresold(precipitationSeekBar.getValue());
        Preferences.getPreferences().setPrecipitationProbabilityThresold(precipitationProbabilitySeekBar.getValue());
        Preferences.getPreferences().setTemperatureThresold(temperatureSeekBar.getValue());
        Preferences.getPreferences().setCloudCoverThresold(cloudCoverSeekBar.getValue());
        Preferences.getPreferences().setVisibilityThresold(visibilitySeekBar.getValue());

        windSwitch =findPreference("wind_switch");
        windGustSwitch = findPreference("wind_gust_switch");
        sunshineSwitch = findPreference("sunshine_switch");
        precipitationSwitch = findPreference("precipitation_switch");

        Preferences.getPreferences().setWindSwitch(windSwitch.isChecked());
        Preferences.getPreferences().setWindGustSwitch(windGustSwitch.isChecked());
        Preferences.getPreferences().setSunshineSwitch(sunshineSwitch.isChecked());
        Preferences.getPreferences().setPrecipitationSwitch(precipitationSwitch.isChecked());

//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindGustThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getPrecipitationThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getPrecipitationProbabilityThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getTemperatureThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getCloudCoverThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getVisibilityThresold()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isPrecipitationSwitch()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isSunshineSwitch()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isWindGustSwitch()));
//        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isWindSwitch()));


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);
    }

    public void resetDefault() {
        windSeekBar.setValue(getResources().getInteger(R.integer.wind_default));
        windGustSeekBar.setValue(getResources().getInteger(R.integer.wind_gust_default));
        precipitationSeekBar.setValue(getResources().getInteger(R.integer.precipitation_default));
        precipitationProbabilitySeekBar.setValue(getResources().getInteger(R.integer.precipitation_probability_default));
        temperatureSeekBar.setValue(getResources().getInteger(R.integer.temperature_default));
        cloudCoverSeekBar.setValue(getResources().getInteger(R.integer.cloud_cover_default));
        visibilitySeekBar.setValue(getResources().getInteger(R.integer.visibility_default));


        windSwitch.setChecked(getResources().getBoolean(R.bool.wind_default));
        windGustSwitch.setChecked(getResources().getBoolean(R.bool.wind_gust_default));
        sunshineSwitch.setChecked(getResources().getBoolean(R.bool.sunshine_default));
        precipitationSwitch.setChecked(getResources().getBoolean(R.bool.precipitation_default));

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        windSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindThresold(progress);
                return true;
            }
        });


        windGustSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindGustThresold(progress);
                return true;
            }
        });


        precipitationSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setPrecipitationThresold(progress);
                return true;
            }
        });

        precipitationProbabilitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setPrecipitationProbabilityThresold(progress);
                return true;
            }
        });

        temperatureSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setTemperatureThresold(progress);
                return true;
            }
        });


        cloudCoverSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setCloudCoverThresold(progress);
                return true;
            }
        });


        visibilitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setVisibilityThresold(progress);
                return true;
            }
        });


        findPreference("wind_switch").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindSwitch(progress);
                return true;
            }
        });

        findPreference("wind_gust_switch").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindGustSwitch(progress);
                return true;
            }
        });

        findPreference("sunshine_switch").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setSunshineSwitch(progress);
                return true;
            }
        });

        findPreference("precipitation_switch").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setPrecipitationSwitch(progress);
                return true;
            }
        });

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i("onPreferenceChange", "changed value is " + newValue);
        return false;
    }
}
