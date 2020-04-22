package com.weather.air_o_inspect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weather.air_o_inspect.DailyForecasts.DailyForecastDataAdapter;
import com.weather.air_o_inspect.Entities.WeatherForecastDaily;
import com.weather.air_o_inspect.Viewmodel.WeatherViewModel;

import java.util.List;

public class WeatherForecastDailyFragment extends Fragment {

    private RecyclerView dailyForecast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast_daily, container, false);

        dailyForecast = view.findViewById(R.id.daily_cards);

        final WeatherViewModel weatherViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(WeatherViewModel.class);
        weatherViewModel.getWeatherForecastDailyLiveData().observe(getActivity(), new Observer<List<WeatherForecastDaily>>() {
            @Override
            public void onChanged(List<WeatherForecastDaily> weatherForecastDailyList) {
                if (weatherForecastDailyList != null && !weatherForecastDailyList.isEmpty()) {
                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    DailyForecastDataAdapter adapter = new DailyForecastDataAdapter();
                    adapter.setDailyForecastList(weatherForecastDailyList);
                    adapter.notifyDataSetChanged();
                    dailyForecast.setLayoutManager(llm);
                    dailyForecast.setAdapter(adapter);
                    dailyForecast.invalidate();
                }
            }
        });

        return view;
    }
}
