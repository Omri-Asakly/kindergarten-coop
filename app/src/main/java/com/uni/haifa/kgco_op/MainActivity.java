package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Parent> parents;
    private FirebaseAuth mAuth;
    private boolean flag = false;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseManager.getInstance().openDataBase(this);
        parents = DataBaseManager.getInstance().getAllParents();
        final SharedPreferences appSettingPrefs = getSharedPreferences("AppSettingPrefs", 0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();
        mAuth = FirebaseAuth.getInstance();
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
                    // validate user using firebase
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Toast.makeText(MainActivity.this, "Hello, "+user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                flag=true;
                            } else {
                                Toast.makeText(MainActivity.this, "Sorry, your password was incorrect.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                    if(flag)
                        Toast.makeText(getApplicationContext(), "Please check the email and password again", Toast.LENGTH_LONG).show();
                }
            }

        });
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserRegister.class);
                startActivity(intent);
            }
        });

    }

    public void updateUI(FirebaseUser user){
        if(user != null){
            DataBaseManager.getInstance().openDataBase(this);
            Intent intent = new Intent(this, MainPage.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        updateUI(currentUser);
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