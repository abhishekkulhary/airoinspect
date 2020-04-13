package com.weather.air_o_inspect.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import com.weather.air_o_inspect.Entities.Preferences;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.MyApp;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.Utils.UtilsWeatherDataRead;
import com.weather.air_o_inspect.Viewmodel.WeatherViewModel;
import com.weather.air_o_inspect.Charts.ChartDataAdapter;
import com.weather.air_o_inspect.Charts.ChartsData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private RecyclerView allCharts;
    private BarChart flyingStatus;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private String dateTitle;

    private int index;

    private List<WeatherUpdate> weatherUpdates;

    static PlaceholderFragment newInstance() {
        return new PlaceholderFragment();
    }

    public void setDateTitle(String dateTitle) {
        this.dateTitle = dateTitle;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setWeatherUpdates(List<WeatherUpdate> weatherUpdates) {
        this.weatherUpdates = weatherUpdates;
    }

    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_fragment, container, false);

        allCharts = view.findViewById(R.id.all_charts);
        flyingStatus = view.findViewById(R.id.fly_status_chart);

        Utils.init(getActivity());

        final WeatherViewModel weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(Objects.requireNonNull(getActivity()).getApplication()).create(WeatherViewModel.class);
        weatherViewModel.getPreferencesLiveData().observe(getActivity(), new Observer<List<Preferences>>() {
                    @Override
                    public void onChanged(List<Preferences> preferences) {
                        if (preferences != null && !preferences.isEmpty()) {
                            final Preferences preferences1 = preferences.get(0);
                            final BarData[] barData = new BarData[]{null};
                            final UtilsWeatherDataRead utilsWeatherDataRead = new UtilsWeatherDataRead();
                            Observable<List<ChartsData>> observable = Observable.defer(new Callable<Observable<List<ChartsData>>>() {
                                @Override
                                public Observable<List<ChartsData>> call() {
                                    Log.i("In Observable", "Hello done till here");
                                    Map<String, ArrayList<Float>> weatherDataRefined = utilsWeatherDataRead.getChartItems(weatherUpdates, dateTitle);
                                    Map<String, ArrayList<BarEntry>> mappedArrayEntries = utilsWeatherDataRead.getMappedArrayListOfEntries(weatherDataRefined);
                                    ArrayList<BarEntry> flyStatusEntries = utilsWeatherDataRead.getFlyStatusBarEntries(Objects.requireNonNull(mappedArrayEntries.get(MyApp.getCOLUMNS()[1])).size());

                                    barData[0] = utilsWeatherDataRead.generateFlyStatusBarData(flyStatusEntries, mappedArrayEntries, preferences1);

                                    ArrayList<BarData> mappedBarData = utilsWeatherDataRead.generateBarData(mappedArrayEntries, preferences1);

                                    final List<ChartsData> chartsDataList = new ArrayList<>();
                                    for (int i = 0; i < mappedBarData.size(); i++) {
                                        chartsDataList.add(new ChartsData(mappedBarData.get(i), MyApp.getLABELS()[i], MyApp.getUNITS()[i], utilsWeatherDataRead.getxValues()));
                                    }
                                    return Observable.just(chartsDataList);
                                }
                            });
                            DisposableObserver<List<ChartsData>> disposableObserver = new DisposableObserver<List<ChartsData>>() {

                                @Override
                                public void onNext(List<ChartsData> chartsDataList) {
                                    final ChartDataAdapter cda = new ChartDataAdapter();
                                    cda.setChartDataList(chartsDataList);
                                    allCharts.setAdapter(cda);

                                    if (barData[0] != null) {
                                        final ArrayList<Long> xValues = utilsWeatherDataRead.getxValues();
                                        BarData data = barData[0];
                                        data.setValueTextColor(Color.BLACK);
                                        data.setHighlightEnabled(false);
                                        data.setValueTextSize(5f);

                                        //            data.calcMinMax();

                                        flyingStatus.getDescription().setEnabled(false);
                                        //            holder.chart.setFitBars(true);
                                        //            holder.chart.setHighlightFullBarEnabled(true);

                                        XAxis xAxis = flyingStatus.getXAxis();

                                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                        xAxis.enableGridDashedLine(10f, 5f, 0f);
                                        xAxis.setGranularityEnabled(true);
                                        xAxis.setGranularity(1f);
                                        xAxis.setLabelCount(data.getEntryCount() + 2, true);
                                        xAxis.setAxisMinimum(data.getXMin() - 1);
                                        xAxis.setAxisMaximum(data.getXMax() + 1);
                                        xAxis.setDrawLabels(true);
                                        xAxis.setValueFormatter(new ValueFormatter() {
                                            @Override
                                            public String getAxisLabel(float value, AxisBase axis) {
                                                if (value >= 0 && value < xValues.size()) {
                                                    return MyApp.getSimpleTimeFormat().format(xValues.get((int) value) * 1000);
                                                }
                                                return "";
                                            }
                                        });

                                        //            xAxis.setLabelRotationAngle(-80f);

                                        xAxis.setCenterAxisLabels(false);

                                        // Y - axis
                                        YAxis rightAxis = flyingStatus.getAxisRight();
                                        rightAxis.setEnabled(true);
                                        rightAxis.setDrawGridLines(false);
                                        rightAxis.setDrawLabels(false);
                                        rightAxis.setDrawLimitLinesBehindData(true);
                                        rightAxis.setMinWidth(35f);
                                        rightAxis.setMaxWidth(40f);

                                        YAxis leftAxis = flyingStatus.getAxisLeft();
                                        leftAxis.enableGridDashedLine(10f, 5f, 0f);
                                        leftAxis.setDrawLimitLinesBehindData(true);
                                        leftAxis.setDrawLabels(false);
                                        leftAxis.setMinWidth(35f);
                                        leftAxis.setMaxWidth(40f);

                                        rightAxis.setAxisMaximum(1f);
                                        leftAxis.setAxisMaximum(1f);

                                        leftAxis.setAxisMinimum(0f);
                                        rightAxis.setAxisMinimum(0f);

                                        Legend legend = flyingStatus.getLegend();
                                        legend.setEnabled(false);

                                        flyingStatus.setScaleEnabled(false);


                                        flyingStatus.setData(data);
                                        flyingStatus.invalidate();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            };
                            disposables.add(
                                    observable.subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribeWith(disposableObserver));

                        }

                    }
        });
        allCharts.setHasFixedSize(true);
        allCharts.setLayoutManager(new LinearLayoutManager(getContext()));
        allCharts.invalidate();
        return view;
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