package com.weather.air_o_inspect.settings;

import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;
import com.weather.air_o_inspect.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
