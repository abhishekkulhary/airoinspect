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

import java.util.Map;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {


    private SharedPreferences sharedPreferences;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.root_preferences);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        Preferences.getPreferences();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);
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
