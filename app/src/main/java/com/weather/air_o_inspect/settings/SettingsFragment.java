package com.weather.air_o_inspect.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import com.weather.air_o_inspect.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.setBackgroundColor(Color.WHITE);
    }
}
