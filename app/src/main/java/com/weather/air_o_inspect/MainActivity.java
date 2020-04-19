package com.weather.air_o_inspect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simmorsal.library.ConcealerNestedScrollView;
import com.weather.air_o_inspect.Charts.ChartDataAdapter;
import com.weather.air_o_inspect.Entities.ChartsData;
import com.weather.air_o_inspect.Entities.WeatherCurrentRequired;
import com.weather.air_o_inspect.Repository.WeatherRespository;
import com.weather.air_o_inspect.Services.ReloadWeatherService;
import com.weather.air_o_inspect.Settings.SettingsFragment;
import com.weather.air_o_inspect.Viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;
    private static RecyclerView allCharts;
    private static BarChart flyingStatusChart;
    private TextView flyingStatusChartName;
    private TextView flyingStatusUnit;

    private ConcealerNestedScrollView nestedScrollView;
    private CardView headerView;
    private CardView statusCard;
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout refreshLayout;

    private String lastRefreshTime = "";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i("MainActivity:", "onRequestPermissionResult");
        if (requestCode == MyApplication.getREQUEST_CODE()) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Log.d("onRequestPermissionsRe:", "Permission Not Granted");
                    return;
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentFlyStatus = findViewById(R.id.current_fly_status);
        currentTemperature = findViewById(R.id.current_temperature);
        currentRainStatus = findViewById(R.id.current_rain_status);
        currentWind = findViewById(R.id.current_wind);
        currentVisibility = findViewById(R.id.current_visibility);
        currentTimePlace = findViewById(R.id.current_time_place);
        nestedScrollView = findViewById(R.id.concealerNSV);
        headerView = findViewById(R.id.crdHeaderView);
        statusCard = findViewById(R.id.status_card);
        progressBar = findViewById(R.id.pBar);
        allCharts = findViewById(R.id.all_charts);

        flyingStatusChart = findViewById(R.id.fly_status_chart);
        flyingStatusChartName = findViewById(R.id.fly_status_chart_name);
        flyingStatusUnit = findViewById(R.id.fly_status_unit_value);
        refreshLayout = findViewById(R.id.swiperefresh);

        progressBar.setVisibility(View.VISIBLE);

        statusCard.setVisibility(View.GONE);
        allCharts.setVisibility(View.GONE);
        headerView.setVisibility(View.GONE);

        floatingActionButton = findViewById(R.id.fab);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MyApplication.getREQUEST_CODE());
        }

        floatingActionButton.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.setFooterView(floatingActionButton, 0);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        headerView.post(new Runnable() {
            @Override
            public void run() {
                nestedScrollView.setHeaderView(headerView, 15);
            }
        });

        final WeatherViewModel weatherViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(WeatherViewModel.class);
        weatherViewModel.getWeatherCurrentLiveData().observe(this, new Observer<WeatherCurrentRequired>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(WeatherCurrentRequired weatherCurrent) {
                if (weatherCurrent != null) {
                    currentTemperature.setText("" + weatherCurrent.getTemperature() + " " + MyApplication.getUNITS().get(MyApplication.getCOLUMNS().indexOf(MyApplication.getTemperatureColumn())));
                    currentRainStatus.setText("" + weatherCurrent.getPrecipProbability() + " " + MyApplication.getUNITS().get(MyApplication.getCOLUMNS().indexOf(MyApplication.getPrecipProbabilityColumn())));
                    currentWind.setText("" + weatherCurrent.getWindSpeed() + " " + MyApplication.getUNITS().get(MyApplication.getCOLUMNS().indexOf(MyApplication.getWindSpeedColumn())));
                    currentVisibility.setText("" + weatherCurrent.getVisibility() + " " + MyApplication.getUNITS().get(MyApplication.getCOLUMNS().indexOf(MyApplication.getVisibilityColumn())));
                    currentTimePlace.setText("" + weatherCurrent.getDateTime());
                    currentFlyStatus.setTextColor(weatherCurrent.getFlyStatus());
                    lastRefreshTime = weatherCurrent.getDateTime();
                }
            }
        });

        Utils.init(this);

        weatherViewModel.getWeatherForecastFlyStatus().observe(this, new Observer<ChartsData>() {
            @Override
            public void onChanged(ChartsData chartsData) {
                if (chartsData != null && !chartsData.getxValues().isEmpty()) {
                    flyingStatusUnit.setText(chartsData.getUnit_value());
                    flyingStatusChartName.setText(chartsData.getChart_name());
                    final ArrayList<Long> xValues = chartsData.getxValues();
                    BarData data = chartsData.getData();
                    data.setValueTextColor(Color.BLACK);
                    data.setHighlightEnabled(true);
                    data.setValueTextSize(5f);

                    XAxis xAxis = flyingStatusChart.getXAxis();

                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setDrawGridLines(false);
                    xAxis.setGranularity(1f);
                    xAxis.setAxisMaxLabels(data.getEntryCount() + 2);
                    xAxis.setLabelCount(data.getEntryCount() + 2);
                    xAxis.setAxisMinimum(data.getXMin() - 1);
                    xAxis.setAxisMaximum(data.getXMax() + 1);
                    xAxis.setDrawLabels(true);
                    xAxis.setValueFormatter(new ValueFormatter() {
                        String temp = "";
                        @Override
                        public String getFormattedValue(float value) {
                            if (value >= 0 && value < xValues.size()) {
                                if (temp.equals(MyApplication.getSimpleDateFormat().format(xValues.get((int) value) * 1000))) {
                                    return MyApplication.getSimpleTimeFormat().format(xValues.get((int) value) * 1000);
                                } else {
                                    temp = MyApplication.getSimpleDateFormat().format(xValues.get((int) value) * 1000);
                                    return MyApplication.getSimpleDateWithTimeInChart().format(xValues.get((int) value) * 1000);
                                }
                            }
                            return "";
                        }
                    });

                    xAxis.setCenterAxisLabels(false);
                    xAxis.setLabelRotationAngle(-80f);

                    // Y - axis
                    YAxis rightAxis = flyingStatusChart.getAxisRight();
                    rightAxis.setEnabled(false);

                    YAxis leftAxis = flyingStatusChart.getAxisLeft();
                    leftAxis.enableGridDashedLine(10f, 5f, 0f);
                    leftAxis.setDrawLimitLinesBehindData(true);
                    leftAxis.setDrawLabels(true);
                    leftAxis.setMinWidth(40f);
                    leftAxis.setMaxWidth(40f);
                    leftAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            return "";
                        }
                    });

                    leftAxis.setAxisMaximum(1f);

                    leftAxis.setAxisMinimum(0f);

                    Legend legend = flyingStatusChart.getLegend();
                    legend.setEnabled(false);


                    flyingStatusChart.getDescription().setEnabled(false);
                    flyingStatusChart.setVisibleXRangeMaximum(24f);
                    flyingStatusChart.setScaleEnabled(false);


                    flyingStatusChart.setData(data);
                    flyingStatusChart.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                    statusCard.setVisibility(View.VISIBLE);
                    allCharts.setVisibility(View.VISIBLE);
                    headerView.setVisibility(View.VISIBLE);

                    flyingStatusChart.invalidate();
                }
            }
        });

        weatherViewModel.getChartsData().observe(this, new Observer<List<ChartsData>>() {
            @Override
            public void onChanged(List<ChartsData> chartsData) {
                LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                final ChartDataAdapter cda = new ChartDataAdapter();
                cda.setChartsDataList(chartsData);
                cda.notifyDataSetChanged();
                allCharts.setLayoutManager(llm);
                allCharts.setAdapter(cda);
                allCharts.invalidate();
            }
        });

        flyingStatusChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                for (int i = 0; i < allCharts.getChildCount(); i++) {
                    BarChart chart = allCharts.getChildAt(i).findViewById(R.id.chart);
                    chart.moveViewToX(flyingStatusChart.getLowestVisibleX());
                    chart.invalidate();
                }
            }
        });

        Intent intent = new Intent(getApplicationContext(), ReloadWeatherService.class);
        startService(intent);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!lastRefreshTime.equals(MyApplication.getSimpleDateWithTimeFormat().format(Calendar.getInstance().getTimeInMillis()))) {
                            System.out.println("Into");
                            new WeatherRespository.RePopulateDbAsyncTask().execute();
                        }
                    }
                });
                weatherViewModel.getWeatherCurrentLiveData().observe(MainActivity.this, new Observer<WeatherCurrentRequired>() {
                    @Override
                    public void onChanged(WeatherCurrentRequired weatherCurrentRequired) {
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        });

    }
}
