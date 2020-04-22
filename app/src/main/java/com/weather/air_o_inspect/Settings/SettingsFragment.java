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
import androidx.preference.SwitchPreferenceCompat;

import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.SeekBar.FloatSeekBarPreference;
import com.weather.air_o_inspect.Viewmodel.WeatherViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {


    private FloatSeekBarPreference sunshineSeekBar;
    private FloatSeekBarPreference temperatureSeekBar;
    private FloatSeekBarPreference windSpeedSeekBar;
    private FloatSeekBarPreference windGustSeekBar;
    private FloatSeekBarPreference precipitationIntensitySeekBar;
    private FloatSeekBarPreference precipitationProbabilitySeekBar;

    private SwitchPreferenceCompat sunshineSwitch;
    private SwitchPreferenceCompat temperatureSwitch;
    private SwitchPreferenceCompat windSpeedSwitch;
    private SwitchPreferenceCompat windGustSwitch;
    private SwitchPreferenceCompat precipitationIntensitySwitch;
    private SwitchPreferenceCompat precipitationProbabilitySwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        sunshineSeekBar = findPreference("sunshine_seek");
        temperatureSeekBar = findPreference("temperature_seek");
        windSpeedSeekBar = findPreference("wind_speed_seek");
        windGustSeekBar = findPreference("wind_gust_seek");
        precipitationIntensitySeekBar = findPreference("precipitation_intensity_seek");
        precipitationProbabilitySeekBar = findPreference("precipitation_probability_seek");

        sunshineSwitch = findPreference("sunshine_switch");
        temperatureSwitch = findPreference("temperature_switch");
        windSpeedSwitch = findPreference("wind_speed_switch");
        windGustSwitch = findPreference("wind_gust_switch");
        precipitationIntensitySwitch = findPreference("precip_intensity_switch");
        precipitationProbabilitySwitch = findPreference("precip_probability_switch");

        final WeatherViewModel weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication()).create(WeatherViewModel.class);

        weatherViewModel.getPreferencesLiveData().observe(requireActivity(), new Observer<List<Preferences>>() {
            @Override
            public void onChanged(final List<Preferences> preferences_1) {
                if (preferences_1 != null && !preferences_1.isEmpty()) {
                    final Preferences preferences = preferences_1.get(0);
                    Log.i("weatherViewM", "Inside preferences ");
                    sunshineSeekBar.setValue(preferences.getSunshineThresold());
                    temperatureSeekBar.setValue(preferences.getTemperatureThresold());
                    windSpeedSeekBar.setValue(preferences.getWindSpeedThresold());
                    windGustSeekBar.setValue(preferences.getWindGustThresold());
                    precipitationIntensitySeekBar.setValue(preferences.getPrecipitationIntensityThresold());
                    precipitationProbabilitySeekBar.setValue(preferences.getPrecipitationProbabilityThresold());

                    sunshineSwitch.setChecked(preferences.getSunshineSwitch());
                    temperatureSwitch.setChecked(preferences.getTemperatureSwitch());
                    windSpeedSwitch.setChecked(preferences.getWindSpeedSwitch());
                    windGustSwitch.setChecked(preferences.getWindGustSwitch());
                    precipitationIntensitySwitch.setChecked(preferences.getPrecipitationIntensitySwitch());
                    precipitationProbabilitySwitch.setChecked(preferences.getPrecipitationProbabilitySwitch());

                    sunshineSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Float progress = (Float) newValue;
                            Preferences preferences_1 = new Preferences(progress,
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    temperatureSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Float progress = (Float) newValue;
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    progress, preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    windSpeedSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Float progress = (Float) newValue;
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), progress,
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    windGustSeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Float progress = (Float) newValue;
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    progress, preferences.getPrecipitationIntensityThresold(), preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    precipitationIntensitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Float progress = (Float) newValue;
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), progress, preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    precipitationProbabilitySeekBar.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Float progress = (Float) newValue;
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(), progress,
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    sunshineSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(),
                                    preferences.getPrecipitationProbabilityThresold(),
                                    progress, preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    temperatureSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(),
                                    preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), progress,
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    windSpeedSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(),
                                    preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    progress, preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    windGustSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(),
                                    preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), progress,
                                    preferences.getPrecipitationIntensitySwitch(), preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    precipitationIntensitySwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(),
                                    preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    progress, preferences.getPrecipitationProbabilitySwitch());

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                    precipitationProbabilitySwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                        @Override
                        public boolean onPreferenceChange(Preference preference, Object newValue) {
                            Boolean progress = Boolean.parseBoolean(String.valueOf(newValue));
                            Preferences preferences_1 = new Preferences(preferences.getSunshineThresold(),
                                    preferences.getTemperatureThresold(), preferences.getWindSpeedThresold(),
                                    preferences.getWindGustThresold(), preferences.getPrecipitationIntensityThresold(),
                                    preferences.getPrecipitationProbabilityThresold(),
                                    preferences.getSunshineSwitch(), preferences.getTemperatureSwitch(),
                                    preferences.getWindSpeedSwitch(), preferences.getWindGustSwitch(),
                                    preferences.getPrecipitationIntensitySwitch(), progress);

                            weatherViewModel.updatePreferences(preferences_1);
                            return true;
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(Color.WHITE);

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
