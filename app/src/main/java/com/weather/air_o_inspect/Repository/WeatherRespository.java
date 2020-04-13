package com.weather.air_o_inspect.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.weather.air_o_inspect.DAO.CurrentStatusDAO;
import com.weather.air_o_inspect.DAO.PreferenceDAO;
import com.weather.air_o_inspect.DAO.WeatherUpdateDAO;
import com.weather.air_o_inspect.Database.WeatherDatabase;
import com.weather.air_o_inspect.Entities.CurrentStatus;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;

import java.util.List;

public class WeatherRespository {

    private WeatherUpdateDAO weatherUpdateDAO;
    private CurrentStatusDAO currentStatusDAO;
    private PreferenceDAO preferenceDAO;

    private LiveData<List<WeatherUpdate>> allWeatherUpdate;
    private LiveData<List<CurrentStatus>> currentStatusLiveData;
    private LiveData<List<Preferences>> preferencesLiveData;

    public WeatherRespository(Application application) {

        WeatherDatabase weatherDatabase = WeatherDatabase.getInstance(application);
        weatherUpdateDAO = weatherDatabase.weatherUpdateDAO();
        currentStatusDAO = weatherDatabase.currentStatusDAO();
        preferenceDAO = weatherDatabase.preferenceDAO();

        allWeatherUpdate = weatherUpdateDAO.getAllWeatherUpdate();

        currentStatusLiveData = currentStatusDAO.getCurrentStatus();

        preferencesLiveData = preferenceDAO.getPreferences();

    }

    public void insertCurrentStatus(CurrentStatus currentStatus) {
        new InsertCurrentStatusAsyncTask(currentStatusDAO).execute(currentStatus);
    }

    public void deleteAllCurrentStatus() {
        new DeleteAllCurrentStatusAsyncTask(currentStatusDAO).execute();
    }

    public void insertWeatherUpdate(List<WeatherUpdate> weatherUpdate) {
        new InsertWeatherUpdateAsyncTask(weatherUpdateDAO).execute(weatherUpdate.toArray(new WeatherUpdate[weatherUpdate.size()]));
    }

    public void deleteAllWeatherUpdate() {
        new DeleteAllWeatherUpdatesAsyncTask(weatherUpdateDAO).execute();
    }

    public void insertPreferences(Preferences preferences) {
        new InsertPreferencesAsyncTask(preferenceDAO).execute(preferences);
    }

    public void deleteAllPreferences() {
        new DeleteAllPreferencesAsyncTask(preferenceDAO).execute();
    }

    public LiveData<List<Preferences>> getPreferencesLiveData() {
        return preferencesLiveData;
    }

    public LiveData<List<CurrentStatus>> getCurrentStatusLiveData() {
        return currentStatusLiveData;
    }

    public LiveData<List<WeatherUpdate>> getAllWeatherUpdate() {
        return allWeatherUpdate;
    }

    private static class InsertCurrentStatusAsyncTask extends AsyncTask<CurrentStatus, Void, Void> {

        private CurrentStatusDAO currentStatusDAO;

        private InsertCurrentStatusAsyncTask(CurrentStatusDAO currentStatusDAO) {
            this.currentStatusDAO = currentStatusDAO;
        }

        @Override
        protected Void doInBackground(CurrentStatus... currentStatuses) {

            currentStatusDAO.insert(currentStatuses[0]);

            return null;
        }
    }
    private static class DeleteAllCurrentStatusAsyncTask extends AsyncTask<Void, Void, Void> {

        private CurrentStatusDAO currentStatusDAO;

        private DeleteAllCurrentStatusAsyncTask(CurrentStatusDAO currentStatusDAO) {
            this.currentStatusDAO = currentStatusDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            currentStatusDAO.deleteAll();

            return null;
        }
    }
    private static class InsertWeatherUpdateAsyncTask extends AsyncTask<WeatherUpdate, Void, Void> {

        private WeatherUpdateDAO weatherUpdateDAO;

        private InsertWeatherUpdateAsyncTask(WeatherUpdateDAO weatherUpdateDAO) {
            this.weatherUpdateDAO = weatherUpdateDAO;
        }

        @Override
        protected Void doInBackground(WeatherUpdate... weatherUpdates) {

            weatherUpdateDAO.insert(weatherUpdates);

            return null;
        }
    }
    private static class DeleteAllWeatherUpdatesAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeatherUpdateDAO weatherUpdateDAO;

        private DeleteAllWeatherUpdatesAsyncTask(WeatherUpdateDAO weatherUpdateDAO) {
            this.weatherUpdateDAO = weatherUpdateDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            weatherUpdateDAO.deleteAll();

            return null;
        }
    }
    private static class InsertPreferencesAsyncTask extends AsyncTask<Preferences, Void, Void> {

        private PreferenceDAO preferenceDAO;

        private InsertPreferencesAsyncTask(PreferenceDAO preferenceDAO) {
            this.preferenceDAO = preferenceDAO;
        }

        @Override
        protected Void doInBackground(Preferences... preferences) {

            preferenceDAO.insert(preferences[0]);

            return null;
        }
    }
    private static class DeleteAllPreferencesAsyncTask extends AsyncTask<Void, Void, Void> {

        private PreferenceDAO preferenceDAO;

        private DeleteAllPreferencesAsyncTask(PreferenceDAO preferenceDAO) {
            this.preferenceDAO = preferenceDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            preferenceDAO.deleteAll();

            return null;
        }
    }
}
