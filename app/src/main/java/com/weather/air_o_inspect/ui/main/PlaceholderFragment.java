package com.weather.air_o_inspect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.BarData;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.chartadapter.ChartDataAdapter;
import com.weather.air_o_inspect.datareadutil.UtilsWeatherDataRead;
import com.weather.air_o_inspect.horizontallistview.HorizontalListView;
import com.weather.air_o_inspect.viewholders.ChartsData;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private HorizontalListView flyingStatus;

    private RecyclerView allCharts;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private String[] LABELS;
    private UtilsWeatherDataRead utilsWeatherDataRead;
    private Map<String, ArrayList<Float>> weatherData;

    public static PlaceholderFragment newInstance(int index, Map<String, ArrayList<Float>> weatherData, String[] LABELS, UtilsWeatherDataRead utilsWeatherDataRead) {

        PlaceholderFragment placeholderFragment = new PlaceholderFragment();
        placeholderFragment.weatherData = weatherData;
        placeholderFragment.LABELS = LABELS;
        placeholderFragment.utilsWeatherDataRead = utilsWeatherDataRead;
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);

        placeholderFragment.setArguments(bundle);

        return placeholderFragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first, container, false);

        allCharts = view.findViewById(R.id.all_charts);

        allCharts.setHasFixedSize(true);
        allCharts.setLayoutManager(new LinearLayoutManager(getContext()));

        Map<String, ArrayList<Float>> weatherDataRefined = utilsWeatherDataRead.getChartItems(weatherData);
        Map<String, Object> mappedArrayEntries = utilsWeatherDataRead.getMappedArrayListOfEntries(weatherDataRefined);
        ArrayList<BarData> mappedBarData = utilsWeatherDataRead.generateBarData(mappedArrayEntries);
        final ArrayList<ChartsData> chartsDataList = new ArrayList<>();

        if (mappedBarData != null) {
            for (int i = 0; i < mappedBarData.size(); i++) {
                chartsDataList.add(new ChartsData(mappedBarData.get(i), LABELS[i], "UNIT"));
            }
        }
        ChartDataAdapter cda = new ChartDataAdapter(chartsDataList);
        allCharts.setAdapter(cda);


//        Observable<ArrayList<ChartsData>> observable = Observable.defer(new Callable<Observable<ArrayList<ChartsData>>>() {
//            @Override
//            public Observable<ArrayList<ChartsData>> call() throws Exception {
//                ArrayList<String[]> weatherData = utilsWeatherDataRead.readWeatherData(filename);
//                Map<String, ArrayList<Float>> yLabelValues = utilsWeatherDataRead.getChartItems(weatherData);
//                Map<String, Object> mappedArrayEntries = utilsWeatherDataRead.getMappedArrayListOfEntries(yLabelValues);
//                ArrayList<BarData> mappedBarData = utilsWeatherDataRead.generateBarData(mappedArrayEntries);
//                final ArrayList<ChartsData> chartsDataList = new ArrayList<>();
//
//                if (mappedBarData != null) {
//                    for (int i = 0; i < mappedBarData.size(); i++) {
//                        chartsDataList.add(new ChartsData(mappedBarData.get(i), LABELS[i], "UNIT"));
//                    }
//                }
//                return Observable.just(chartsDataList);
//            }
//        });
//
//        DisposableObserver<ArrayList<ChartsData>> disposableObserver = new DisposableObserver<ArrayList<ChartsData>>() {
//            @Override
//            public void onNext(ArrayList<ChartsData> chartsDataList) {
//                ChartDataAdapter cda = new ChartDataAdapter(chartsDataList);
//                allCharts.setAdapter(cda);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//            }
//        };
//
//        disposables.add(
//                observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(disposableObserver));

        allCharts.invalidate();

        return view;
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
    }

    @Override

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