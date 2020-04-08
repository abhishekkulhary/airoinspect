package com.weather.air_o_inspect;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.BarData;
import com.weather.air_o_inspect.datareadutil.UtilsWeatherDataRead;
import com.weather.air_o_inspect.chartadapter.ChartDataAdapter;
import com.weather.air_o_inspect.horizontallistview.HorizontalListView;
import com.weather.air_o_inspect.viewholders.ChartsData;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ALL")
public class FirstFragment extends Fragment {

    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;

    private Button nextPage;

    private HorizontalListView flyingStatus;

    private RecyclerView allCharts;

    private MyApp myApp;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public FirstFragment() {
        this.myApp = new MyApp();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allCharts =  view.findViewById(R.id.all_charts);
        allCharts.setHasFixedSize(true);
        allCharts.setLayoutManager(new LinearLayoutManager(getContext()));

        String filename = myApp.getFilename()[0];
        final UtilsWeatherDataRead utilsWeatherDataRead = new UtilsWeatherDataRead(filename, getContext());

        Observable<ArrayList<ChartsData>> observable = Observable.defer(new Callable<ObservableSource<ArrayList<ChartsData>>>() {
            @Override
            public ObservableSource<ArrayList<ChartsData>> call() {
                Map<String, ArrayList<Float>> yLabelValues = utilsWeatherDataRead.getChartItems();
                Map<String, Object> mappedArrayEntries = utilsWeatherDataRead.getMappedArrayListOfEntries(yLabelValues);
                ArrayList<BarData> mappedBarData = utilsWeatherDataRead.generateBarData(mappedArrayEntries);
                ArrayList<ChartsData> chartsDataList = new ArrayList<>();
                if (mappedBarData != null) {
                    for (int i = 0; i < mappedBarData.size(); i++) {
                        chartsDataList.add(new ChartsData(mappedBarData.get(i), myApp.getLABELS()[i], "UNIT"));
                    }

                    return Observable.just(chartsDataList);
                }
                return null;
            }
        });

        DisposableObserver<ArrayList<ChartsData>> disposableObserver = new DisposableObserver<ArrayList<ChartsData>>() {
            @Override
            public void onNext(ArrayList<ChartsData> chartsDataList) {
                ChartDataAdapter cda = new ChartDataAdapter(chartsDataList);
                allCharts.setAdapter(cda);
                Log.i("postAsync", "Size of chartsdatalist " + chartsDataList.size());
            }

            @Override
            public void onError(Throwable e) {
                Log.e("error", "error:  ", e);

            }

            @Override
            public void onComplete() {
            }
        };

        disposables.add(
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(disposableObserver));

        allCharts.invalidate();

        nextPage = view.findViewById(R.id.next_page);

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) Objects.requireNonNull(getActivity())).loadFragment(new SecondFragment());
            }
        });

    }

    public TextView getCurrentFlyStatus() {
        return currentFlyStatus;
    }

    public void setCurrentFlyStatus(TextView currentFlyStatus) {
        this.currentFlyStatus = currentFlyStatus;
    }

    public TextView getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(TextView currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public TextView getCurrentRainStatus() {
        return currentRainStatus;
    }

    public void setCurrentRainStatus(TextView currentRainStatus) {
        this.currentRainStatus = currentRainStatus;
    }

    public TextView getCurrentWind() {
        return currentWind;
    }

    public void setCurrentWind(TextView currentWind) {
        this.currentWind = currentWind;
    }

    public TextView getCurrentVisibility() {
        return currentVisibility;
    }

    public void setCurrentVisibility(TextView currentVisibility) {
        this.currentVisibility = currentVisibility;
    }

    public TextView getCurrentTimePlace() {
        return currentTimePlace;
    }

    public void setCurrentTimePlace(TextView currentTimePlace) {
        this.currentTimePlace = currentTimePlace;
    }

    public Button getNextPage() {
        return nextPage;
    }

    public void setNextPage(Button nextPage) {
        this.nextPage = nextPage;
    }

    public HorizontalListView getFlyingStatus() {
        return flyingStatus;
    }

    public void setFlyingStatus(HorizontalListView flyingStatus) {
        this.flyingStatus = flyingStatus;
    }

    public RecyclerView getAllCharts() {
        return allCharts;
    }

    public void setAllCharts(RecyclerView allCharts) {
        this.allCharts = allCharts;
    }@Override

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
