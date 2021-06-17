package com.uni.haifa.kgco_op;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class weeklySchedule extends AppCompatActivity {
    Date selectedDate;
    String castDate;
    TextView morningTxt;
    TextView eveningTxt;
    List<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        scheduleList = DataBaseManager.getInstance().getAllSchedules();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        selectedDate = new Date();
        castDate = dateFormat.format(selectedDate);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Weekly Schedule");
        ab.setDisplayHomeAsUpEnabled(true);


        morningTxt = findViewById(R.id.editTextTextPersonName);
        eveningTxt = findViewById(R.id.editTextTextPersonName3);
        editNames();
        ImageView btnInfo = findViewById(R.id.infoMorning);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weeklySchedule.this, tripInfo.class);
                intent.putExtra("date", castDate);
                intent.putExtra("time", "morning");
                startActivity(intent);
            }
        });

        ImageView infoEvening = findViewById(R.id.infoEvening);
        infoEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weeklySchedule.this, tripInfo.class);
                intent.putExtra("date", castDate);
                intent.putExtra("time", "evening");
                startActivity(intent);
            }
        });
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                selectedDate.setMonth(month);
                selectedDate.setDate(dayOfMonth);
                selectedDate.setYear(year-1900);

                castDate = dateFormat.format(selectedDate);
                editNames();

            }
        });

        Button addToScheduleBtn = findViewById(R.id.addToScheduleBtn);
        addToScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weeklySchedule.this, FixSchedule.class);
                intent.putExtra("date", castDate);
                startActivity(intent);
            }
        });
    }

    private void editNames(){
        for(Schedule s : scheduleList){
            if(s.compare(selectedDate)){
                morningTxt.setText(s.getMorning());
                eveningTxt.setText(s.getEvening());
                return;
            }
        }
        morningTxt.setText("None");
        eveningTxt.setText("None");
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
                                        Intent intent = new Intent(weeklySchedule.this, MainActivity.class);
                                        weeklySchedule.this.startActivity(intent);
                                    }
                                }).create().show();
                return true;
            case R.id.btnSetting :
                Intent intent2 = new Intent(weeklySchedule.this, Settings.class);
                startActivity(intent2);
                return true;
            case R.id.btnHome:
                Intent intent3 = new Intent(weeklySchedule.this, MainPage.class);
                startActivity(intent3);
                return true;
        }
        return false;
    }
}