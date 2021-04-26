package com.uni.haifa.kgco_op;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import static com.uni.haifa.kgco_op.R.id.btnNav;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ImageView btnNav= findViewById(R.id.btnNav);
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
                NavigationView nav= findViewById(R.id.Navigation);
                nav.setVisibility(View.INVISIBLE);
            }
        });
        Button btnSchedule1= findViewById(R.id.navBtnFullSchedule);
        btnSchedule1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainPage.this, weeklySchedule.class);
                startActivity(intent1);
            }
        });
        Button btnSchedule2= findViewById(R.id.btnFullSchedule);
        btnSchedule2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainPage.this, weeklySchedule.class);
                startActivity(intent2);
            }
        });
    }
}