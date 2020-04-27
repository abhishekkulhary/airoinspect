package com.weather.air_o_inspect.Database;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherCurrent;
import com.weather.air_o_inspect.Entities.WeatherForecast;
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;
import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.MyLocation;

import java.util.List;

@Database(entities = {WeatherCurrent.class, WeatherForecast.class, Preferences.class, WeatherForecastDaily.class},
        version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {

    private static final Handler handler = new Handler();
    private static WeatherDatabase instance;
    private static Runnable runnable;

    public static synchronized WeatherDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    WeatherDatabase.class, "weather_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    if (MyApplication.getInstance().isInternetAvailable(context)) {
                                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                                                != PackageManager.PERMISSION_GRANTED
                                                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                                                != PackageManager.PERMISSION_GRANTED) {

                                            handler.postDelayed(runnable, 1000 * 2);
                                            return;
                                        }
                                        MyLocation.LocationResult locationResult = new MyLocation.LocationResult() {
                                            @Override
                                            public void gotLocation(Location location) {
                                                new PopulateDbAsyncTask(instance, location).execute();
                                            }
                                        };
                                        MyLocation myLocation = new MyLocation();
                                        myLocation.getLocation(context, locationResult);
                                    } else {
                                        handler.postDelayed(runnable, 1000 * 2);
                                    }

                                }
                            };
                            handler.post(runnable);
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract WeatherForecastDAO weatherUpdateDAO();

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeatherForecastDAO weatherForecastDAO;
        private Location location;

        PopulateDbAsyncTask(WeatherDatabase db, Location location) {
            weatherForecastDAO = db.weatherUpdateDAO();
            this.location = location;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            DatabaseUtils databaseUtils = new DatabaseUtils();

            String dataFromUrl = databaseUtils.getDataFromUrl(location.getLatitude() + "," + location.getLongitude(), MyApplication.getQuery());

            List<WeatherForecast> weatherForecastList = databaseUtils.convertJsonToWeatherForecastList(dataFromUrl);
            WeatherCurrent weatherCurrent = databaseUtils.convertJsonToWeatherCurrent(dataFromUrl);
            List<WeatherForecastDaily> weatherForecastDailyList = databaseUtils.convertJsonToWeatherForecastDaily(dataFromUrl);

            weatherForecastDAO.insertWeatherForecast(weatherForecastList.toArray(new WeatherForecast[weatherForecastList.size()]));
            weatherForecastDAO.insertWeatherCurrent(weatherCurrent);
            weatherForecastDAO.insertPreferences(new Preferences());
            weatherForecastDAO.insertWeatherForecastDaily(weatherForecastDailyList.toArray(new WeatherForecastDaily[weatherForecastDailyList.size()]));

            return null;
        }
    }

}
