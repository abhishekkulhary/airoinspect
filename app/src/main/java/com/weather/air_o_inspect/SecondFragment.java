package com.weather.air_o_inspect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weather.air_o_inspect.asynctask.AsyncWeatherDataRetrieval;
import com.weather.air_o_inspect.horizontallistview.HorizontalListView;

import java.util.concurrent.TimeUnit;

public class SecondFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static MainActivity mainActivity;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private TextView currentFlyStatus;
    private TextView currentTemperature;
    private TextView currentRainStatus;
    private TextView currentWind;
    private TextView currentVisibility;
    private TextView currentTimePlace;

    private Button nextPage;

    private HorizontalListView flyingStatus;

    private RecyclerView allCharts;

    static SecondFragment getInstance(Context context, MainActivity activity) {

        SecondFragment.context = context;
        SecondFragment.mainActivity = activity;
//
        return new SecondFragment();
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allCharts = view.findViewById(R.id.all_charts);
        allCharts.setHasFixedSize(true);
        allCharts.setLayoutManager(new LinearLayoutManager(context));
        allCharts.invalidate();


        String filename = "forecast2.csv";
        new AsyncWeatherDataRetrieval(context, filename, getAllCharts()).execute();

        nextPage = view.findViewById(R.id.go_back);

        nextPage.setText("Go Back");

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SecondFragment.mainActivity.loadFragment(FirstFragment.getInstance(SecondFragment.context, SecondFragment.mainActivity));
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
    }
}
