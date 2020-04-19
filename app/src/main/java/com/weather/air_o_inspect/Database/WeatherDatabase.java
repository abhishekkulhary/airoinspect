package com.weather.air_o_inspect.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.MyApplication;

import java.util.List;

@Database(entities = {WeatherCurrent.class, WeatherForecast.class, Preferences.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    private static WeatherDatabase instance;
    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static synchronized WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class, "weather_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public abstract WeatherForecastDAO weatherUpdateDAO();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeatherForecastDAO weatherForecastDAO;

        PopulateDbAsyncTask(WeatherDatabase db) {
            weatherForecastDAO = db.weatherUpdateDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseUtils databaseUtils = new DatabaseUtils();

            String dataFromUrl = databaseUtils.getDataFromUrl(MyApplication.getLongLat(), MyApplication.getQuery());

            List<WeatherForecast> weatherForecastList = databaseUtils.convertJsonToWeatherForecastList(dataFromUrl);
            WeatherCurrent weatherCurrent = databaseUtils.convertJsonToWeatherCurrent(dataFromUrl);

            weatherForecastDAO.insertWeatherForecast(weatherForecastList.toArray(new WeatherForecast[weatherForecastList.size()]));
            weatherForecastDAO.insertWeatherCurrent(weatherCurrent);
            weatherForecastDAO.insertPreferences(new Preferences());

            return null;
        }
    }

}
