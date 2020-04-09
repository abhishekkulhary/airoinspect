package com.weather.air_o_inspect.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.weather.air_o_inspect.MyApp;
import com.weather.air_o_inspect.R;
import com.weather.air_o_inspect.datareadutil.UtilsWeatherDataRead;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static int[] TAB_TITLES;
    private final Context mContext;
    private MyApp myApp;
    private UtilsWeatherDataRead utilsWeatherDataRead;

    public SectionsPagerAdapter(Context context, int count, FragmentManager fm, UtilsWeatherDataRead utilsWeatherDataRead, MyApp myApp) {
        super(fm, count);
        mContext = context;
        TAB_TITLES = new int[]{R.string.tab_1, R.string.tab_2};
        this.utilsWeatherDataRead = utilsWeatherDataRead;
        this.myApp = myApp;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position, myApp.getFilename()[position], myApp.getLABELS(), utilsWeatherDataRead);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return myApp.getFilename().length;
    }
}