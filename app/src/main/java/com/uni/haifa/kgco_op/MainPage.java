package com.uni.haifa.kgco_op;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;
import java.util.List;

public class MainPage extends AppCompatActivity {
    TextView todayMorningTxt;
    TextView todayEveningTXt;
    TextView tomorrowMorningTxt;
    TextView tomorrowEveningTxt;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseManager.getInstance().openDataBase(this);
        final int[] flag = {0};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Button btnDM = findViewById(R.id.btnDM);


        todayMorningTxt = findViewById(R.id.etMorningPerson);
        todayEveningTXt = findViewById(R.id.editTextTextPersonName2);
        tomorrowMorningTxt = findViewById(R.id.etMorningPerson2);
        tomorrowEveningTxt = findViewById(R.id.editTextTextPersonName5);

        List<Schedule> scheduleList = DataBaseManager.getInstance().getAllSchedules();
        Date today = new Date();
        for(Schedule s : scheduleList){
            System.out.println(s);
            if(s.compare(today)){
                todayMorningTxt.setText(s.getMorning());
                todayEveningTXt.setText(s.getEvening());
                break;
            }
       }
        Date tomorrow = Schedule.getTomorrow();
        for(Schedule s : scheduleList){
            if(s.compare(tomorrow)){
                tomorrowMorningTxt.setText(s.getMorning());
                tomorrowEveningTxt.setText(s.getEvening());
                break;
            }
        }
        final SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();
        final Boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);
        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btnDM.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_day));
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btnDM.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_night));

        }
        btnDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefsEdit.putBoolean("NightMode", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefsEdit.putBoolean("NightMode", true);
                }
                sharedPrefsEdit.apply();
            }
        });
        ImageView btnNav = findViewById(R.id.btnBack);
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = findViewById(R.id.Navigation);
                nav.setVisibility(View.VISIBLE);
            }
        });

        ImageView btnHideNav = findViewById(R.id.btnHideNav);
        btnHideNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = findViewById(R.id.Navigation);
                nav.setVisibility(View.INVISIBLE);
            }
        });
        Button navBtnHome = findViewById(R.id.navBtnHome);
        navBtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = findViewById(R.id.Navigation);
                nav.setVisibility(View.INVISIBLE);
            }
        });
        Button btnSchedule1 = findViewById(R.id.navBtnFullSchedule);
        btnSchedule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainPage.this, weeklySchedule.class);
                startActivity(intent1);
            }
        });

        Button btnSchedule2 = findViewById(R.id.btnFullSchedule);
        btnSchedule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainPage.this, weeklySchedule.class);
                startActivity(intent2);
            }
        });
        Button addUser = findViewById(R.id.userBtn);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainPage.this, AddUser.class);
                startActivity(intent2);
            }
        });
        Button delete = findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainPage.this, Delete.class);
                startActivity(intent2);
            }
        });
        Button edit = findViewById(R.id.editBtn);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainPage.this, EditParent.class);
                startActivity(intent2);
            }
        });
        Button userList = findViewById(R.id.userListBtn);
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, UserList.class);
                startActivity(intent);
            }
        });
        Button settings = findViewById(R.id.navBtnSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainPage.this, SettingsActivity.class);
                startActivity(intent2);
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null) {
            if (currentUser.getEmail() != null) {
                edit.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                addUser.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnLogout:
                new AlertDialog.Builder(this)
                        .setTitle("Logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(MainPage.this, MainActivity.class);
                                        MainPage.this.startActivity(intent);
                                    }
                                }).create().show();
                return true;
            case R.id.btnSetting :
                Intent intent2 = new Intent(MainPage.this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.btnHome:
                Intent intent3 = new Intent(MainPage.this, MainPage.class);
                startActivity(intent3);
                return true;
        }
        return false;
    }
    @Override
    protected void onResume() {
        DataBaseManager.getInstance().openDataBase(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        DataBaseManager.getInstance().closeDataBase();
        super.onPause();
    }
}