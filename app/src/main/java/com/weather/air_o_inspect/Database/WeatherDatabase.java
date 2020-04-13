package com.weather.air_o_inspect.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.weather.air_o_inspect.DAO.CurrentStatusDAO;
import com.weather.air_o_inspect.DAO.PreferenceDAO;
import com.weather.air_o_inspect.DAO.WeatherUpdateDAO;
import com.weather.air_o_inspect.Entities.CurrentStatus;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.MyApp;
import com.weather.air_o_inspect.Utils.Utils;

import java.util.List;

@Database(entities = {CurrentStatus.class, WeatherUpdate.class, Preferences.class}, version = 1)
public abstract class WeatherDatabase extends RoomDatabase {

    private static WeatherDatabase instance;

    public abstract CurrentStatusDAO currentStatusDAO();

    public abstract WeatherUpdateDAO weatherUpdateDAO();

    public abstract PreferenceDAO preferenceDAO();

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

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
            new PopulateDbPreferencesAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private CurrentStatusDAO currentStatusDAO;
        private WeatherUpdateDAO weatherUpdateDAO;

        public PopulateDbAsyncTask(WeatherDatabase db) {
            currentStatusDAO = db.currentStatusDAO();
            weatherUpdateDAO = db.weatherUpdateDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Utils utils = new Utils();

            String dataFromUrl = utils.getDataFromUrl(MyApp.getLongLat(), MyApp.getQuery());

            List<WeatherUpdate> weatherUpdateList = utils.convertJsonToWeatherUpdateList(dataFromUrl);
            CurrentStatus currentStatus = utils.convertJsonToCurrentStatus(dataFromUrl);

            weatherUpdateDAO.deleteAll();
            currentStatusDAO.deleteAll();

            weatherUpdateDAO.insert(weatherUpdateList.toArray(new WeatherUpdate[weatherUpdateList.size()]));
            currentStatusDAO.insert(currentStatus);

            return null;
        }
    }

    public static class PopulateDbPreferencesAsyncTask extends AsyncTask<Void, Void, Void> {
        private PreferenceDAO preferenceDAO;

        public PopulateDbPreferencesAsyncTask(WeatherDatabase db) {
            preferenceDAO = db.preferenceDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Utils utils = new Utils();

            Preferences preferences = utils.getPreferences();

            preferenceDAO.deleteAll();
            preferenceDAO.insert(preferences);

            return null;
        }
    }

}
