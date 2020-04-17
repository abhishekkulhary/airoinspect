package com.weather.air_o_inspect.Repository;

import android.os.AsyncTask;

import com.weather.air_o_inspect.Database.WeatherForecastDAO;
import com.weather.air_o_inspect.Entities.WeatherCurrent;

public class WeatherCurrentAsyncTask extends AsyncTask<WeatherCurrent, Void, Void> {

    private final static Integer INSERT_OPERTION = 0;
    private final static Integer UPDATE_OPERTION = 1;
    private final static Integer DELETE_OPERTION = 2;

    private WeatherForecastDAO weatherForecastDAO;
    private Integer operation;

    WeatherCurrentAsyncTask(WeatherForecastDAO weatherForecastDAO, Integer operation) {
        this.weatherForecastDAO = weatherForecastDAO;
        this.operation = operation;
    }

    @Override
    protected Void doInBackground(WeatherCurrent... weatherCurrents) {
        if (operation.equals(INSERT_OPERTION)) {
            weatherForecastDAO.insertWeatherCurrent(weatherCurrents[0]);
        } else if (operation.equals(UPDATE_OPERTION)) {
            weatherForecastDAO.updateWeatherCurrent(weatherCurrents[0]);
        } else if (operation.equals(DELETE_OPERTION)) {
            weatherForecastDAO.deleteWeatherCurrent(weatherCurrents[0]);
        }
        return null;
    }
}
