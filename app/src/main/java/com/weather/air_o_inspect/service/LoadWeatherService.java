package com.weather.air_o_inspect.Service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.weather.air_o_inspect.Entities.CurrentStatus;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.MyApp;
import com.weather.air_o_inspect.Repository.WeatherRespository;
import com.weather.air_o_inspect.Utils.Utils;

import java.util.List;


public class LoadWeatherService extends Service {

    Runnable periodicUpdate;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("LoadWeatherNoLimitSe:", "onStartCommand");

        //TODO: 1. Try Combining Handler, and thread within and try making the handler to run everyday at specific time
        final Handler handler = new Handler();
        periodicUpdate = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(periodicUpdate, 1000 * 60 * MyApp.getTimeDelay()); // schedule next wake up every X mins

                new NewPopulateDbAsyncTask(new WeatherRespository(getApplication())).execute();

            }
        };
        handler.postDelayed(periodicUpdate, 1000 * 60 * MyApp.getTimeDelay()); // schedule next wake up every X mins


        return START_STICKY;
    }

    public static class NewPopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private WeatherRespository weatherRespository;

        public NewPopulateDbAsyncTask(WeatherRespository weatherRespository) {
            this.weatherRespository = weatherRespository;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Utils utils = new Utils();

            String dataFromUrl = utils.getDataFromUrl(MyApp.getLongLat(), MyApp.getQuery());

            List<WeatherUpdate> weatherUpdateList = utils.convertJsonToWeatherUpdateList(dataFromUrl);
            CurrentStatus currentStatus = utils.convertJsonToCurrentStatus(dataFromUrl);

            this.weatherRespository.deleteAllWeatherUpdate();
            this.weatherRespository.deleteAllCurrentStatus();

            this.weatherRespository.insertWeatherUpdate(weatherUpdateList);
            this.weatherRespository.insertCurrentStatus(currentStatus);

            return null;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("LoadWeatherNoLimitSe:", "onTaskRemoved");
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("LoadWeatherNoLimitSe:", "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("LoadWeatherNoLimitSe:", "onDestroy");
        super.onDestroy();
    }
}
