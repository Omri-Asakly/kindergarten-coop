package com.uni.haifa.kgco_op;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

public class Settings extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Settings");
        ab.setDisplayHomeAsUpEnabled(true);

        Switch dm=findViewById(R.id.switchDarkmode);
        final SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();
        final Boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            dm.setChecked(true);
//            dm.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_day));
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            dm.setChecked(false);
//            dm.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_night));

        }
        dm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dm.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefsEdit.putBoolean("NightMode", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefsEdit.putBoolean("NightMode", true);
                }
                sharedPrefsEdit.apply();
            }
        });
    }

}