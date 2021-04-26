package com.uni.haifa.kgco_op;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class weeklySchedule extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_schedule);
        ImageView btnNav= findViewById(R.id.btnNav2);
        btnNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavigationView nav= findViewById(R.id.Navigation);
                nav.setVisibility(View.VISIBLE);
            }
        });
        ImageView btnHideNav= findViewById(R.id.btnHideNav);
        btnHideNav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavigationView nav= findViewById(R.id.Navigation);
                nav.setVisibility(View.INVISIBLE);
            }
        });
        Button navBtnHome= findViewById(R.id.navBtnHome);
        navBtnHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(weeklySchedule.this, MainPage.class);
                startActivity(intent);
            }
        });
        Button btnSchedule1= findViewById(R.id.navBtnFullSchedule);
        btnSchedule1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NavigationView nav= findViewById(R.id.Navigation);
                nav.setVisibility(View.INVISIBLE);
            }
        });

    }
}