package com.weather.air_o_inspect.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.weather.air_o_inspect.MyApp;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LoadWeatherService extends Service{

    private final CompositeDisposable disposables = new CompositeDisposable();

    MyApp myApp = new MyApp();
    Utils utils = new Utils();

    Observable<String> observable = Observable.defer(new Callable<ObservableSource<String>>() {
        @Override
        public ObservableSource<String> call() throws Exception {
            return Observable.just(utils.getDataFromUrlWriteToCSV(myApp.getLongLat(), myApp.getQuery()));
        }
    });

    DisposableObserver<String> disposableObserver = new DisposableObserver<String>() {
        @Override
        public void onNext(String data) {
            if (data != null) {
                utils.saveCSVFile(getApplicationContext(), data, myApp.getFilename());
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
        }
    };

    //TODO: 1. Try Combining Handler, and thread within CompositeDisposable disposables.

    Handler handler = new Handler();
    public Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1000 * 60 * myApp.getTimeDelay()); // schedule next wake up every X mins

            disposables.add(
                    observable.subscribeOn(Schedulers.io())
                            .subscribeWith(disposableObserver));

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        handler.post(periodicUpdate);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
