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

import com.weather.air_o_inspect.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {


    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        SeekBarPreference precipitationSeekBar = findPreference("precipitation_seek");
        Preferences.getPreferences().setPrecipitationSeek(precipitationSeekBar.getValue());
        SeekBarPreference windSeekBar = findPreference("wind_seek");
        Preferences.getPreferences().setWindSeek(windSeekBar.getValue());
        SeekBarPreference windGustSeekBar = findPreference("wind_gust_seek");
        Preferences.getPreferences().setWindGustSeek(windGustSeekBar.getValue());

        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindGustSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getPrecipitationSeek()));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);
    }

    public void resetDefault() {
        SeekBarPreference windSeekBar = findPreference("wind_seek");
        windSeekBar.setValue(getResources().getInteger(R.integer.wind_default));
        SeekBarPreference windGustSeekBar = findPreference("wind_gust_seek");
        windGustSeekBar.setValue(getResources().getInteger(R.integer.wind_gust_default));
        SeekBarPreference precipitationSeekBar = findPreference("precipitation_seek");
        precipitationSeekBar.setValue(getResources().getInteger(R.integer.precipitation_default));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        findPreference("wind_seek").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindSeek(progress);
                return true;
            }
        });


        findPreference("wind_gust_seek").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setWindGustSeek(progress);
                return true;
            }
        });


        findPreference("precipitation_seek").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                final int progress = Integer.parseInt(String.valueOf(newValue));
                preference.setSummary(String.format("Current value: %d", progress));
                Preferences.getPreferences().setPrecipitationSeek(progress);
                return true;
            }
        });
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getWindGustSeek()));
        Log.i("onCreatePref", String.valueOf(Preferences.getPreferences().getPrecipitationSeek()));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.i("onPreferenceChange", "changed value is " + newValue);
        return false;
    }
}
