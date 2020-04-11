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

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        SeekBarPreference windSeekBar = findPreference("wind_seek");
        SeekBarPreference windGustSeekBar = findPreference("wind_gust_seek");
        SeekBarPreference precipitationSeekBar = findPreference("precipitation_seek");
        Preferences.getPreferences().setWindSeek(windSeekBar.getValue());
        Preferences.getPreferences().setWindGustSeek(windGustSeekBar.getValue());
        Preferences.getPreferences().setPrecipitationSeek(precipitationSeekBar.getValue());
        SwitchPreferenceCompat windSwitch = findPreference("wind_switch");
        SwitchPreferenceCompat windGustSwitch = findPreference("wind_gust_switch");
        SwitchPreferenceCompat sunshineSwitch = findPreference("sunshine_switch");
        SwitchPreferenceCompat precipitationSwitch = findPreference("precipitation_switch");
        Preferences.getPreferences().setWindSwitch(windSwitch.isChecked());
        Preferences.getPreferences().setWindGustSwitch(windGustSwitch.isChecked());
        Preferences.getPreferences().setSunshineSwitch(sunshineSwitch.isChecked());
        Preferences.getPreferences().setPrecipitationSwitch(precipitationSwitch.isChecked());
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindGustSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getPrecipitationSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isPrecipitationSwitch()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isSunshineSwitch()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isWindGustSwitch()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isWindSwitch()));


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);
    }

    public void resetDefault() {
        SeekBarPreference windSeekBar = findPreference("wind_seek");
        SeekBarPreference windGustSeekBar = findPreference("wind_gust_seek");
        SeekBarPreference precipitationSeekBar = findPreference("precipitation_seek");
        windSeekBar.setValue(getResources().getInteger(R.integer.wind_default));
        windGustSeekBar.setValue(getResources().getInteger(R.integer.wind_gust_default));
        precipitationSeekBar.setValue(getResources().getInteger(R.integer.precipitation_default));

        SwitchPreferenceCompat windSwitch = findPreference("wind_switch");
        SwitchPreferenceCompat windGustSwitch = findPreference("wind_gust_switch");
        SwitchPreferenceCompat sunshineSwitch = findPreference("sunshine_switch");
        SwitchPreferenceCompat precipitationSwitch = findPreference("precipitation_switch");
        windSwitch.setChecked(getResources().getBoolean(R.bool.wind_default));
        windGustSwitch.setChecked(getResources().getBoolean(R.bool.wind_gust_default));
        sunshineSwitch.setChecked(getResources().getBoolean(R.bool.sunshine_default));
        precipitationSwitch.setChecked(getResources().getBoolean(R.bool.precipitation_default));

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        findPreference("wind_seek").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindSeek(progress);
                return true;
            }
        });


        findPreference("wind_gust_seek").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindGustSeek(progress);
                return true;
            }
        });


        findPreference("precipitation_seek").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
//                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setPrecipitationSeek(progress);
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
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindGustSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getPrecipitationSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isWindSwitch()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isWindGustSwitch()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isSunshineSwitch()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().isPrecipitationSwitch()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i("onPreferenceChange", "changed value is " + newValue);
        return false;
    }
}
