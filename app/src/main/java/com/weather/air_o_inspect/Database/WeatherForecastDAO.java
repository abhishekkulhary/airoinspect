package com.weather.air_o_inspect.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;

import java.util.List;

@Dao
public interface WeatherForecastDAO {

    @Insert
    void insertWeatherForecast(WeatherForecast... weatherForecast);

    @Update
    void updateWeatherForecast(WeatherForecast weatherForecast);

    @Delete
    void deleteWeatherForecast(WeatherForecast weatherForecast);

    @Query("SELECT * FROM weather_forecast ORDER BY timeInMillis ASC")
    LiveData<List<WeatherForecast>> getAllWeatherForecast();

    @Insert
    void insertWeatherCurrent(WeatherCurrent weatherCurrent);

    @Delete
    void deleteWeatherCurrent(WeatherCurrent weatherCurrent);

    @Update
    void updateWeatherCurrent(WeatherCurrent weatherCurrent);

    @Query("SELECT * FROM weather_current")
    LiveData<List<WeatherCurrent>> getWeatherCurrent();

    @Insert
    void insertPreferences(Preferences preferences);

    @Delete
    void deletePreferences(Preferences preferences);

    @Update
    void updatePreferences(Preferences preferences);

    @Query("SELECT * FROM preferences")
    LiveData<List<Preferences>> getPreferences();

    @Insert
    void insertWeatherForecastDaily(WeatherForecastDaily... weatherForecastDaily);

    @Update
    void updateWeatherForecastDaily(WeatherForecastDaily weatherForecastDaily);

    @Delete
    void deleteWeatherForecastDaily(WeatherForecastDaily weatherForecastDaily);

    @Query("SELECT * FROM weather_forecast_daily ORDER BY timeInMillis ASC")
    LiveData<List<WeatherForecastDaily>> getAllWeatherForecastDaily();

}