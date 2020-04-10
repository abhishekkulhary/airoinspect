package com.weather.air_o_inspect.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.weather.air_o_inspect.MainActivity;
import com.weather.air_o_inspect.MyApp;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("All")
public class LoadWeatherService extends Service {

    private final CompositeDisposable disposables = new CompositeDisposable();

    MyApp myApp;
    Utils utils = new Utils();

    Runnable periodicUpdate;
    private final Integer broadcastTem = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i("LoadWeatherNoLimitService:", "onStartCommand");
        //TODO: 1. Try Combining Handler, and thread within CompositeDisposable disposables.

        myApp = new MyApp();

        boolean isRepeat = intent.getBooleanExtra("isRepeat", true);

        final Observable<String> observable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                String data = utils.getDataFromUrlWriteToCSV(myApp.getLongLat(), myApp.getQuery());
                return Observable.just(data);
            }
        });

        final DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String data) {
                if (data != null && !data.equals("")) {
                    utils.saveCSVFile(getApplicationContext(), data, myApp.getFilename());
                }

                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(MainActivity.mBroadcastRepeatAction);
                sendBroadcast(broadcastIntent);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
            }
        };

        if (isRepeat) {
            final Handler handler = new Handler();
            periodicUpdate = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(periodicUpdate, 1000 * 60 * myApp.getTimeDelay()); // schedule next wake up every X mins

                    disposables.add(
                            observable.subscribeOn(Schedulers.io())
                                    .subscribeWith(disposableObserver));


                }
            };
            handler.postDelayed(periodicUpdate, 1000 * 60 * myApp.getTimeDelay()); // schedule next wake up every X mins
        } else {
            final Handler handler = new Handler();
            periodicUpdate = new Runnable() {
                @Override
                public void run() {

                    handler.postDelayed(periodicUpdate, 1000 * 60 * myApp.getTimeDelay()); // schedule next wake up every X mins

                    disposables.add(
                            observable.subscribeOn(Schedulers.io())
                                    .subscribeWith(disposableObserver));


                }
            };
            handler.post(periodicUpdate);
        }


        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("LoadWeatherNoLimitService:", "onTaskRemoved");
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("LoadWeatherNoLimitService:", "onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.i("LoadWeatherNoLimitService:", "onDestroy");
        super.onDestroy();
        disposables.clear();
    }
}
