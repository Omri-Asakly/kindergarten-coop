package com.uni.haifa.kgco_op;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


public class tripInfo extends AppCompatActivity {
    CheckBox child1;
    CheckBox child2;
    CheckBox child3;
    List<Schedule> scheduleList;
    Date date;
    Schedule schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBaseManager.getInstance().openDataBase(this);
        setContentView(R.layout.activity_trip_info);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Trip Info");
        ab.setDisplayHomeAsUpEnabled(true);
        child1 = findViewById(R.id.child1);
        child2 = findViewById(R.id.child2);
        child3 = findViewById(R.id.child3);
        scheduleList = DataBaseManager.getInstance().getAllSchedules();
        Bundle b = getIntent().getExtras();
        String value = b.getString("time");
        try {
            date = Schedule.stringToDate(b.getString("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Schedule s : scheduleList) {
            if (s.compare(date)) {
                schedule = s;
                break;
            }
        }

        if (value.equals("morning")) {
            try {
                child1.setText(schedule.getMorningKids().get(0));
                child2.setText(schedule.getMorningKids().get(1));
                child3.setText(schedule.getMorningKids().get(2));

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (value.equals("evening")) {
            try {
                child1.setText(schedule.getEveningKids().get(0));
                child2.setText(schedule.getEveningKids().get(1));
                child3.setText(schedule.getEveningKids().get(2));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        ImageView btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(tripInfo.this, weeklySchedule.class);
//                startActivity(intent);
//            }
//        });
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