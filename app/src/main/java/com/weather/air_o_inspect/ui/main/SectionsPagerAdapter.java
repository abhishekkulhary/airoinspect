package com.weather.air_o_inspect.ui.main;

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

import com.weather.air_o_inspect.MyApp;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.Utils.UtilsWeatherDataRead;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static String[] TAB_TITLES;
    private MyApp myApp;
    private Context mContext;
    private UtilsWeatherDataRead utilsWeatherDataRead;
    private Map<String, Map<String, ArrayList<Float>>> yLabelValues;

    public SectionsPagerAdapter(Context context, int count, FragmentManager fm, UtilsWeatherDataRead utilsWeatherDataRead, MyApp myApp, Map<String, Map<String, ArrayList<Float>>> yLabelValues) {
        super(fm, count);
        this.utilsWeatherDataRead = utilsWeatherDataRead;
        this.myApp = myApp;
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
        return PlaceholderFragment.newInstance(position, this.yLabelValues.get(TAB_TITLES[position]), this.myApp.getLABELS(), utilsWeatherDataRead);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }

    public String[] convert(Set<String> setOfString) {

        // Create String[] of size of setOfString
        String[] arrayOfString = new String[setOfString.size()];

        // Copy elements from set to string array
        // using advanced for loop
        int index = 0;
        for (String str : setOfString)
            arrayOfString[index++] = str;

        for (int i = 0, j = arrayOfString.length - 1; i <= j; i++, j--) {

            String temp = arrayOfString[i];
            arrayOfString[i] = arrayOfString[j];
            arrayOfString[j] = temp;

        }

        // return the formed String[]
        return arrayOfString;
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_layout_viewpager, null);

        // View Binding
        TextView title = view.findViewById(R.id.tv_title);
        TextView subtitle = view.findViewById(R.id.tv_sub_title);

        // Data Binding
        if (position < myApp.getTAB_SUBTEXT().length) {
            title.setText(myApp.getTAB_SUBTEXT()[position]);
            subtitle.setText(TAB_TITLES[position]);
        } else {
            title.setText(TAB_TITLES[position]);
        }

        return view;
    }
}