package com.weather.air_o_inspect.Repository;

import android.os.AsyncTask;

import com.weather.air_o_inspect.Database.WeatherForecastDAO;
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;

public class WeatherForeCastDailyAsyncTask extends AsyncTask<WeatherForecastDaily, Void, Void> {

    private final static Integer INSERT_OPERTION = 0;
    private final static Integer UPDATE_OPERTION = 1;
    private final static Integer DELETE_OPERTION = 2;

    private WeatherForecastDAO weatherForecastDAO;
    private Integer operation;

    WeatherForeCastDailyAsyncTask(WeatherForecastDAO weatherForecastDAO, Integer operation) {
        this.weatherForecastDAO = weatherForecastDAO;
        this.operation = operation;
    }

    @Override
    protected Void doInBackground(WeatherForecastDaily... weatherForecastsDaily) {
        if (operation.equals(INSERT_OPERTION)) {
            for (WeatherForecastDaily forecastDaily : weatherForecastsDaily) {
                weatherForecastDAO.insertWeatherForecastDaily(forecastDaily);
            }
        } else if (operation.equals(UPDATE_OPERTION)) {
            for (WeatherForecastDaily forecastDaily : weatherForecastsDaily) {
                weatherForecastDAO.updateWeatherForecastDaily(forecastDaily);
            }
        } else if (operation.equals(DELETE_OPERTION)) {
            for (WeatherForecastDaily forecastDaily : weatherForecastsDaily) {
                weatherForecastDAO.deleteWeatherForecastDaily(forecastDaily);
            }
        }
        return null;
    }
}
