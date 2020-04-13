package com.weather.air_o_inspect.Viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.weather.air_o_inspect.Entities.CurrentStatus;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.Repository.WeatherRespository;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRespository weatherRespository;
    private LiveData<List<WeatherUpdate>> allWeatherUpdates;
    private LiveData<List<CurrentStatus>> currentStatusLiveData;
    private LiveData<List<Preferences>> preferencesLiveData;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherRespository = new WeatherRespository(application);

        allWeatherUpdates = weatherRespository.getAllWeatherUpdate();

        currentStatusLiveData = weatherRespository.getCurrentStatusLiveData();

        preferencesLiveData = weatherRespository.getPreferencesLiveData();
    }

    public void insertCurrentStatus(CurrentStatus currentStatus) {
        weatherRespository.insertCurrentStatus(currentStatus);
    }

    public void deleteAllCurrentStatus() {
        weatherRespository.deleteAllCurrentStatus();
    }

    public void insertWeatherUpdate(List<WeatherUpdate> weatherUpdate) {
        weatherRespository.insertWeatherUpdate(weatherUpdate);
    }

    public void deleteAllWeatherUpdate() {
        weatherRespository.deleteAllWeatherUpdate();
    }

    public void insertPreferences(Preferences preferences) {
        weatherRespository.insertPreferences(preferences);
    }

    public void deleteAllPreferences() {
        weatherRespository.deleteAllPreferences();
    }

    public LiveData<List<CurrentStatus>> getCurrentStatusLiveData() {
        return currentStatusLiveData;
    }

    public LiveData<List<WeatherUpdate>> getAllWeatherUpdate() {
        return allWeatherUpdates;
    }

    public LiveData<List<Preferences>> getPreferencesLiveData() {
        return preferencesLiveData;
    }
}
