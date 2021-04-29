package com.uni.haifa.kgco_op;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnDM = findViewById(R.id.btnDM);

        final SharedPreferences appSettingPrefs= getSharedPreferences("AppSettingPrefs",  0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();
        final Boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);
        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btnDM.setText("Disable Dark Mode");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btnDM.setText("Enable Dark Mode");
        }
        btnDM.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefsEdit.putBoolean("NightMode", false);
                    sharedPrefsEdit.apply();

                    btnDM.setText("Enable Dark Mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefsEdit.putBoolean("NightMode", true);
                    sharedPrefsEdit.apply();

                    btnDM.setText("Disable Dark Mode");
                }
            }
        });
        Button loginBtn= this.<Button>findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText editTextEmail=(EditText)findViewById(R.id.editTextEmail);
                String email=editTextEmail.getText().toString();
                EditText editTextPassword=(EditText)findViewById(R.id.editTextPassword);
                String password=editTextPassword.getText().toString();
                if ((email.equals("admin")) && (password.equals("admin"))){
                    Toast.makeText(getApplicationContext(),"Hello Admin",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent);
                }
                else{
     //              Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),password ,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent);
                }
            }

        });

    }





}