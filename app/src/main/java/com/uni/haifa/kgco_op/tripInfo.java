package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

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
        setContentView(R.layout.activity_trip_info);

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
        for(Schedule s : scheduleList){
            if(s.compare(date)){
                schedule = s;
                break;
            }
        }
        if(value.equals("morning")) {
            child1.setText(schedule.getMorningKids()[0]);
            child2.setText(schedule.getMorningKids()[1]);
            child3.setText(schedule.getMorningKids()[2]);

        }
        if(value.equals("evening")) {
            child1.setText(schedule.getEveningKids()[0]);
            child2.setText(schedule.getEveningKids()[1]);
            child3.setText(schedule.getEveningKids()[2]);

        }


        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tripInfo.this, weeklySchedule.class);
                startActivity(intent);



            }
        });



    }

}