package com.weather.air_o_inspect.Services;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.Observer;

import com.weather.air_o_inspect.Entities.ChartsData;
import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.Repository.WeatherRespository;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReloadWeatherService extends LifecycleService {

    private final Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("LoadWeatherNoLimitSe:", "onStartCommand");

        final WeatherRespository weatherRespository = new WeatherRespository(getApplication());

        runnable = new Runnable() {
            @Override
            public void run() {

                handler.postDelayed(runnable, 1000 * 60 * MyApplication.getTimeDelay());

                weatherRespository.getWeatherForecastIndividually().observe(ReloadWeatherService.this, new Observer<List<ChartsData>>() {

                    @Override
                    public void onChanged(List<ChartsData> chartsDataList) {
                        if (chartsDataList != null && !chartsDataList.isEmpty()) {
                            ChartsData data = chartsDataList.get(0);
                            if (data != null) {
                                ArrayList<Long> xValues = data.getxValues();

                                String dateWithHour = MyApplication.getSimpleDateFormat().format(xValues.get(0) * 1000) + MyApplication.getSimpleTimeFormat().format(xValues.get(0) * 1000);
                                String dateWithHour1 = MyApplication.getSimpleDateFormat().format(Calendar.getInstance().getTimeInMillis()) + MyApplication.getSimpleTimeFormat().format(Calendar.getInstance().getTimeInMillis());
                                if (!dateWithHour.equals(dateWithHour1)) {
                                    new WeatherRespository.RePopulateDbAsyncTask().execute();
                                }
                            }
                        }
                    }
                });
            }
        };

        handler.post(runnable);

        return START_STICKY;
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
    public IBinder onBind(@NotNull Intent intent) {
        Log.i("LoadWeatherNoLimitSe:", "onBind");
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        Log.i("LoadWeatherNoLimitSe:", "onDestroy");
        super.onDestroy();
    }
}
