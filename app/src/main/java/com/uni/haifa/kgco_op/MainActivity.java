package com.uni.haifa.kgco_op;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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