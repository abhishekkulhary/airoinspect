package com.weather.air_o_inspect.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.weather.air_o_inspect.Database.WeatherDatabase;
import com.weather.air_o_inspect.Entities.WeatherUpdate;
import com.weather.air_o_inspect.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<String> TAB_TITLES;
    private List<WeatherUpdate> weatherUpdates;
    private Context mContext;

    public SectionsPagerAdapter(Context context, int count, FragmentManager fm) {
        super(fm, count);
        this.mContext = context;
    }

    public void setWeatherUpdates(List<WeatherUpdate> weatherUpdates) {
        this.weatherUpdates = weatherUpdates;
    }
    public void setTAB_TITLES(List<String> TAB_TITLES) {
        this.TAB_TITLES = TAB_TITLES;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        PlaceholderFragment placeholderFragment = PlaceholderFragment.newInstance();
        placeholderFragment.setDateTitle(TAB_TITLES.get(position));
        placeholderFragment.setIndex(position);
        placeholderFragment.setWeatherUpdates(weatherUpdates);
        return placeholderFragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    public View getTabView(int position) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_layout_viewpager, null);

        TextView title = view.findViewById(R.id.tv_title);
        TextView subtitle = view.findViewById(R.id.tv_sub_title);

        subtitle.setText(TAB_TITLES.get(position));
        String title1 = getDayFromDate(TAB_TITLES.get(position));
        title.setText(title1);

        return view;
    }

    @SuppressLint("SimpleDateFormat")
    private String getDayFromDate(String date) {
        try {
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            Calendar oldDay = Calendar.getInstance();
            oldDay.setTime(Objects.requireNonNull(new SimpleDateFormat("dd MMM yyyy").parse(date)));

            int todayYear = today.get(Calendar.YEAR);
            int todayDay = today.get(Calendar.DAY_OF_YEAR);
            int oldDayYear = oldDay.get(Calendar.YEAR);
            int oldDayDay = oldDay.get(Calendar.DAY_OF_YEAR);

            if (todayYear == oldDayYear) {
                int value = oldDayDay - todayDay;
                if (value == -2) {
                    return "Day Before";
                } else if (value == -1) {
                    new WeatherDatabase.PopulateDbAsyncTask(WeatherDatabase.getInstance(mContext.getApplicationContext())).execute();
                    return "Yesterday";
                } else if (value == 0) {
                    return "Today";
                } else if (value == 1) {
                    return "Tomorrow";
                } else if (value == 2) {
                    return "Day After";
                }
            }
        } catch (ParseException e) {
            Log.i("getDayFromDate", "Error while converting to day string", e);
        }
        return null;
    }
}