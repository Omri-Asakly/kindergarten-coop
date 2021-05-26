package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Parent> parents;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseManager.getInstance().openDataBase(this);
        parents = DataBaseManager.getInstance().getAllParents();
        final SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();

        Button loginBtn = this.<Button>findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
                String email = editTextEmail.getText().toString();
                EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
                String password = editTextPassword.getText().toString();
                if ((email.equals("admin")) && (password.equals("admin"))) {
                    Toast.makeText(getApplicationContext(), "Hello Admin", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent);
                } else {
                    for (Parent p : parents) {
                        if (p.getEmail().equals(email) && p.getPassword().equals(password)) {
                            flag = false;
                            Toast.makeText(getApplicationContext(), "Welcome "+p.getUserName(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainActivity.this, MainPage.class);
                            startActivity(intent);
                        }
                    }
                    if(flag)
                        Toast.makeText(getApplicationContext(), "Please check the email and password again", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

}