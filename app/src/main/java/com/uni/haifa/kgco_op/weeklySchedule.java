package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        ImageView btnNav = findViewById(R.id.btnNav2);
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = findViewById(R.id.Navigation);
                nav.setVisibility(View.VISIBLE);
            }
        });

        morningTxt = findViewById(R.id.editTextTextPersonName);
        eveningTxt = findViewById(R.id.editTextTextPersonName3);
        editNames();
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
                Intent intent = new Intent(weeklySchedule.this, MainPage.class);
                startActivity(intent);
            }
        });
        Button btnSchedule1 = findViewById(R.id.navBtnFullSchedule);
        btnSchedule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationView nav = findViewById(R.id.Navigation);
                nav.setVisibility(View.INVISIBLE);
            }
        });
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
}