package com.weather.air_o_inspect.charts;

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

import com.weather.air_o_inspect.MyApplication;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.service.UtilsWeatherDataRead;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static List<String> TAB_TITLES;
    private MyApplication myApplication;
    private Context mContext;
    private UtilsWeatherDataRead utilsWeatherDataRead;
    private Map<String, Map<String, ArrayList<Float>>> yLabelValues;

    private TextView title;
    private TextView subtitle;

    public SectionsPagerAdapter(Context context, int count, FragmentManager fm, UtilsWeatherDataRead utilsWeatherDataRead, MyApplication myApplication, Map<String, Map<String, ArrayList<Float>>> yLabelValues) {
        super(fm, count);
        this.utilsWeatherDataRead = utilsWeatherDataRead;
        this.myApplication = myApplication;
        this.mContext = context;
        this.yLabelValues = yLabelValues;
        TAB_TITLES = convert(this.yLabelValues.keySet());
        Log.i("TAB_TILES", this.yLabelValues.keySet().size() + "");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return ChartFragment.newInstance(position, this.yLabelValues.get(TAB_TITLES.get(position)), this.myApplication.getLABELS(), this.myApplication.getUNITS(), utilsWeatherDataRead);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return this.yLabelValues.keySet().size();
    }

    private List<String> convert(Set<String> setOfString) {

        // Create List<String> of size of setOfString
        List<String> arrayOfString = new ArrayList<>(setOfString);
        Collections.sort(arrayOfString);
        // return the formed List<String>
        return arrayOfString;
    }

    public View getTabView(int position) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_layout_viewpager, null);

        // View Binding
        title = view.findViewById(R.id.tv_title);
        subtitle = view.findViewById(R.id.tv_sub_title);

        // Data Binding
        if (position < myApplication.getTAB_SUBTEXT().length) {
            title.setText(myApplication.getTAB_SUBTEXT()[position]);
            subtitle.setText(TAB_TITLES.get(position));
        } else {
            title.setText(TAB_TITLES.get(position));
        }

        return view;
    }
}