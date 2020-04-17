package com.weather.air_o_inspect.Repository;


import android.os.AsyncTask;

import com.weather.air_o_inspect.Database.WeatherForecastDAO;
import com.weather.air_o_inspect.Entities.WeatherForecast;

public class WeatherForeCastAsyncTask extends AsyncTask<WeatherForecast, Void, Void> {

    private final static Integer INSERT_OPERTION = 0;
    private final static Integer UPDATE_OPERTION = 1;
    private final static Integer DELETE_OPERTION = 2;

    private WeatherForecastDAO weatherForecastDAO;
    private Integer operation;

    WeatherForeCastAsyncTask(WeatherForecastDAO weatherForecastDAO, Integer operation) {
        this.weatherForecastDAO = weatherForecastDAO;
        this.operation = operation;
    }

    @Override
    protected Void doInBackground(WeatherForecast... weatherForecasts) {
        if (operation.equals(INSERT_OPERTION)) {
            for (WeatherForecast forecast : weatherForecasts) {
                weatherForecastDAO.insertWeatherForecast(forecast);
            }
        } else if (operation.equals(UPDATE_OPERTION)) {
            for (WeatherForecast forecast : weatherForecasts) {
                weatherForecastDAO.updateWeatherForecast(forecast);
            }
        } else if (operation.equals(DELETE_OPERTION)) {
            for (WeatherForecast forecast : weatherForecasts) {
                weatherForecastDAO.deleteWeatherForecast(forecast);
            }
        }
        return null;
    }
}
