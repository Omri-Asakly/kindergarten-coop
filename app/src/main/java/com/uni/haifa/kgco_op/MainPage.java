package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final int[] flag = {0};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ImageView btnNav = findViewById(R.id.btnNav);
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
        Button userList = findViewById(R.id.userListBtn);
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, UserList.class);
                startActivity(intent);
            }
        });
    }


}