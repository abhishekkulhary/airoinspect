package com.weather.air_o_inspect.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.weather.air_o_inspect.Entities.CurrentStatus;

import java.util.List;

@Dao
public interface CurrentStatusDAO {

    @Insert
    void insert(CurrentStatus currentStatus);

    @Query("DELETE FROM current_status")
    void deleteAll();

    @Query("SELECT * FROM current_status")
    LiveData<List<CurrentStatus>> getCurrentStatus();
}
