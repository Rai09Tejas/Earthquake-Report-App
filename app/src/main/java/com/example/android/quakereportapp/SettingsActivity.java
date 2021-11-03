package com.example.android.quakereportapp;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.quakereportapp.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment {

    }
}