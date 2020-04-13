package com.weather.air_o_inspect.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.weather.air_o_inspect.Entities.WeatherUpdate;

import java.util.List;

@Dao
public interface WeatherUpdateDAO {

    @Insert
    void insert(WeatherUpdate... weatherUpdate);

    @Query("DELETE FROM weather_update")
    void deleteAll();

    @Query("SELECT * FROM weather_update ORDER BY time ASC")
    LiveData<List<WeatherUpdate>> getAllWeatherUpdate();

}