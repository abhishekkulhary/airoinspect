package com.weather.air_o_inspect.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.weather.air_o_inspect.Entities.Preferences;

import java.util.List;

@Dao
public interface PreferenceDAO {
    @Insert
    void insert(Preferences preferences);

    @Query("DELETE FROM preferences")
    void deleteAll();

    @Query("SELECT * FROM preferences")
    LiveData<List<Preferences>> getPreferences();
}
