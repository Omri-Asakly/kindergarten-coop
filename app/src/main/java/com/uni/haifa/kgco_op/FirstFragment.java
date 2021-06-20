package com.uni.haifa.kgco_op;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;


public class FirstFragment extends Fragment {


    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_first, container, false);


        Switch dm=view.findViewById(R.id.switchDarkmode);
        final SharedPreferences appSettingPrefs = this.getActivity().getSharedPreferences("AppSettingPrefs", 0);
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
        return view;
    }

}
