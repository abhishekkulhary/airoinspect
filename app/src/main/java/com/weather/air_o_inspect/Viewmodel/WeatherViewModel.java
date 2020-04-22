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
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;
import com.weather.air_o_inspect.Repository.WeatherRespository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRespository weatherRespository;

    private LiveData<WeatherCurrentRequired> weatherCurrentLiveData;
    private LiveData<List<Preferences>> preferencesLiveData;
    private LiveData<List<ChartsData>> weatherForecastLiveData;
    private LiveData<ChartsData> weatherForecastFlyStatusLiveData;
    private LiveData<List<WeatherForecastDaily>> weatherForecastDailyLiveData;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRespository = new WeatherRespository(application);

        weatherCurrentLiveData = weatherRespository.getWeatherCurrentRequiredLiveData();

        preferencesLiveData = weatherRespository.getPreferencesLiveData();

        weatherForecastLiveData = weatherRespository.getWeatherForecastIndividually();

        weatherForecastFlyStatusLiveData = weatherRespository.getWeatherForecastFlyStatus();

        weatherForecastDailyLiveData = weatherRespository.getWeatherForecastDailyLiveData();

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

    public void insertWeatherForecastDaily(List<WeatherForecastDaily> weatherForecastDaily) {
        for (WeatherForecastDaily forecastDaily : weatherForecastDaily) {
            weatherRespository.insertWeatherForecastDaily(forecastDaily);
        }
    }

    public void updateWeatherForecastDaily(List<WeatherForecastDaily> weatherForecastDaily) {
        for (WeatherForecastDaily forecastDaily : weatherForecastDaily) {
            weatherRespository.updateWeatherForecastDaily(forecastDaily);
        }
    }

    public void deleteAllWeatherForecastDaily() {
        weatherRespository.deleteAllWeatherForecastDaily();
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
        return weatherForecastLiveData;
    }

    public LiveData<ChartsData> getWeatherForecastFlyStatus() {
        return weatherForecastFlyStatusLiveData;
    }

    public LiveData<List<WeatherForecastDaily>> getWeatherForecastDailyLiveData() {
        return weatherForecastDailyLiveData;
    }
}
