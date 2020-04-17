package com.weather.air_o_inspect.Settings;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.Viewmodel.WeatherViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {


    private SeekBarPreference precipitationIntensitySeekBar;
    private SeekBarPreference precipitationProbabilitySeekBar;
    private SeekBarPreference temperatureSeekBar;
    private SeekBarPreference pressureSeekBar;
    private SeekBarPreference windSpeedSeekBar;
    private SeekBarPreference windGustSeekBar;
    private SeekBarPreference cloudCoverSeekBar;
    private SeekBarPreference visibilitySeekBar;

    private SwitchPreferenceCompat precipitationIntensitySwitch;
    private SwitchPreferenceCompat precipitationProbabilitySwitch;
    private SwitchPreferenceCompat temperatureSwitch;
    private SwitchPreferenceCompat pressureSwitch;
    private SwitchPreferenceCompat windSpeedSwitch;
    private SwitchPreferenceCompat windGustSwitch;
    private SwitchPreferenceCompat cloudCoverSwitch;
    private SwitchPreferenceCompat visibilitySwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        precipitationIntensitySeekBar = findPreference("precipitation_intensity_seek");
        precipitationProbabilitySeekBar = findPreference("precipitation_probability_seek");
        temperatureSeekBar = findPreference("temperature_seek");
        pressureSeekBar = findPreference("pressure_seek");
        windSpeedSeekBar = findPreference("wind_speed_seek");
        windGustSeekBar = findPreference("wind_gust_seek");
        cloudCoverSeekBar = findPreference("cloud_cover_seek");
        visibilitySeekBar = findPreference("visibility_seek");

        precipitationIntensitySwitch = findPreference("precip_intensity_switch");
        precipitationProbabilitySwitch = findPreference("precip_probability_switch");
        temperatureSwitch = findPreference("temperature_switch");
        pressureSwitch = findPreference("pressure_switch");
        windSpeedSwitch = findPreference("wind_speed_switch");
        windGustSwitch = findPreference("wind_gust_switch");
        cloudCoverSwitch = findPreference("cloud_cover_switch");
        visibilitySwitch = findPreference("visibility_switch");
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);

        final WeatherViewModel weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication()).create(WeatherViewModel.class);

        weatherViewModel.getPreferencesLiveData().observe(getActivity(), new Observer<List<Preferences>>() {
            @Override
            public void onChanged(final List<Preferences> preferences_1) {

                final Preferences preferences = preferences_1.get(0);
                Log.i("weatherViewM", "Inside preferences ");
                precipitationIntensitySeekBar.setValue(Math.round(preferences.getPrecipitationIntensityThresold()));
                precipitationProbabilitySeekBar.setValue(Math.round(preferences.getPrecipitationProbabilityThresold()));
                temperatureSeekBar.setValue(Math.round(preferences.getTemperatureThresold()));
                pressureSeekBar.setValue(Math.round(preferences.getPressureThresold()));
                windSpeedSeekBar.setValue(Math.round(preferences.getWindSpeedThresold()));
                windGustSeekBar.setValue(Math.round(preferences.getWindGustThresold()));
                cloudCoverSeekBar.setValue(Math.round(preferences.getCloudCoverThresold()));
                visibilitySeekBar.setValue(Math.round(preferences.getVisibilityThresold()));

                precipitationIntensitySwitch.setChecked(preferences.getPrecipitationIntensitySwitch());
                precipitationProbabilitySwitch.setChecked(preferences.getPrecipitationProbabilitySwitch());
                temperatureSwitch.setChecked(preferences.getTemperatureSwitch());
                pressureSwitch.setChecked(preferences.getPressureSwitch());
                windSpeedSwitch.setChecked(preferences.getWindSpeedSwitch());
                windGustSwitch.setChecked(preferences.getWindGustSwitch());
                cloudCoverSwitch.setChecked(preferences.getCloudCoverSwitch());
                visibilitySwitch.setChecked(preferences.getVisibilitySwitch());

                precipitationIntensitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(progress, preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(), preferences.getWindSpeedThresold(),
                                preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                precipitationProbabilitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), progress,
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(), preferences.getWindSpeedThresold(),
                                preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                temperatureSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                progress, preferences.getPressureThresold(), preferences.getWindSpeedThresold(),
                                preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                pressureSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), progress, preferences.getWindSpeedThresold(),
                                preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                windSpeedSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(), progress,
                                preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                windGustSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), progress, preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                cloudCoverSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), progress, preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                visibilitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Float progress = Float.parseFloat(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), progress,
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                precipitationIntensitySwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                progress, preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                precipitationProbabilitySwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), progress, preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                temperatureSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), progress,
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                pressureSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                progress, preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                windSpeedSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), progress, preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                windGustSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), progress, preferences.getCloudCoverSwitch(),
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                cloudCoverSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), progress,
                                preferences.getVisibilitySwitch());

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
                visibilitySwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                        Preferences preferences_1 = new Preferences(preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                preferences.getTemperatureThresold(), preferences.getPressureThresold(),
                                preferences.getWindSpeedThresold(), preferences.getWindGustThresold(), preferences.getCloudCoverThresold(), preferences.getVisibilityThresold(),
                                preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch(), preferences.getTemperatureSwitch(),
                                preferences.getPressureSwitch(), preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(), preferences.getCloudCoverSwitch(),
                                progress);

                        weatherViewModel.updatePreferences(preferences_1);
                        return true;
                    }
                });
            }
        });

    }

//    public void resetDefault() {
//
//        DatabaseUtils utils = new DatabaseUtils();
//
//        Preferences preferences = utils.getPreferences();
//
//        final WeatherViewModel weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication()).create(WeatherViewModel.class);
//
//        weatherViewModel.updatePreferences(preferences);
//
//    }
}
