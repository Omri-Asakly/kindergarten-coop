package com.uni.haifa.kgco_op;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Settings");
        ab.setDisplayHomeAsUpEnabled(true);
        Button firstBtn = findViewById(R.id.btn1);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send data to fragment
                FragmentManager fm = getFragmentManager();
                Bundle args = new Bundle();
                FirstFragment fragmet1 = new FirstFragment();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragmet1);
                t.addToBackStack(null);
                t.commit();

            }
        });
        Button secondBtn = findViewById(R.id.btn2);
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                SecondFragment fragmet2 = new SecondFragment();
                FragmentTransaction t = fm.beginTransaction();
                t.replace(R.id.root_layout, fragmet2);
                t.addToBackStack(null);
                t.commit();

            }
        });
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
                                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                                        SettingsActivity.this.startActivity(intent);
                                    }
                                }).create().show();
                return true;
            case R.id.btnSetting :
                Intent intent2 = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.btnHome:
                Intent intent3 = new Intent(SettingsActivity.this, MainPage.class);
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
