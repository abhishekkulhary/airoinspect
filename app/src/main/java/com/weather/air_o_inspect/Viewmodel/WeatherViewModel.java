package com.weather.air_o_inspect.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weather.air_o_inspect.Entities.ChartsData;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherCurrentRequired;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.Repository.WeatherRespository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRespository weatherRespository;

    private LiveData<WeatherCurrentRequired> weatherCurrentLiveData;
    private LiveData<List<Preferences>> preferencesLiveData;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRespository = new WeatherRespository(application);

        weatherCurrentLiveData = weatherRespository.getWeatherCurrentRequiredLiveData();

        preferencesLiveData = weatherRespository.getPreferencesLiveData();
    }

    public void insertWeatherCurrent(WeatherCurrent weatherCurrent) {
        weatherRespository.insertWeatherCurrent(weatherCurrent);
    }

    public void updateWeatherCurrent(WeatherCurrent weatherCurrent) {
        weatherRespository.updateWeatherCurrent(weatherCurrent);
    }

    public void deleteWeatherCurrent(WeatherCurrent weatherCurrent) {
        weatherRespository.deleteWeatherCurrent(weatherCurrent);
    }

    public void insertWeatherForecast(List<WeatherForecast> weatherForecast) {
        for (WeatherForecast forecast : weatherForecast) {
            weatherRespository.insertWeatherForecast(forecast);
        }
    }

    public void updateWeatherForecast(List<WeatherForecast> weatherForecast) {
        for (WeatherForecast forecast : weatherForecast) {
            weatherRespository.updateWeatherForecast(forecast);
        }
    }

    public void deleteAllWeatherForecast() {
        weatherRespository.deleteAllWeatherForecast();
    }

    public void insertPreferences(Preferences preferences) {
        weatherRespository.insertPreferences(preferences);
    }

    public void updatePreferences(Preferences preferences) {
        weatherRespository.updatePreferences(preferences);
    }

    public void deletePreferences(Preferences preferences) {
        weatherRespository.deletePreferences(preferences);
    }

    public LiveData<WeatherCurrentRequired> getWeatherCurrentLiveData() {
        return weatherCurrentLiveData;
    }

    public LiveData<List<Preferences>> getPreferencesLiveData() {
        return preferencesLiveData;
    }

    public LiveData<List<ChartsData>> getChartsData() {
        return weatherRespository.getWeatherForecastIndividually();
    }

    public LiveData<ChartsData> getWeatherForecastFlyStatus() {
        return weatherRespository.getWeatherForecastFlyStatus();
    }
}
