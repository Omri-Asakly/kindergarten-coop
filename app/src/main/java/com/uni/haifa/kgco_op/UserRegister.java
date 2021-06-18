package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserRegister extends AppCompatActivity {
    private TextView name;
    private TextView email;
    private TextView password;
    private TextView date;
    private List<Parent> parents;
    private FirebaseAuth mAuth;
    private boolean flag = true;
    private Date dateF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Insert Parent");
        ab.setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        DataBaseManager.getInstance().openDataBase(this);
        parents = DataBaseManager.getInstance().getAllParents();
        setContentView(R.layout.activity_user_register);
        name = findViewById(R.id.userTxt);
        email = findViewById(R.id.mailTxt);
        password = findViewById(R.id.passTxt);
        date = findViewById(R.id.textDate);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Button save = findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Parent p : DataBaseManager.getInstance().getAllParents()) {
                    if (p.getEmail().equals(email.getText().toString())) {
                        flag = false;
                        Toast.makeText(UserRegister.this, "Email already in use", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if (flag) {
                    dateF = null;
                    try {
                        dateF = formatter.parse(date.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(UserRegister.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Parent parent = new Parent(name.getText().toString(), email.getText().toString(), password.getText().toString(), dateF);
                                DataBaseManager.getInstance().createParent(parent);
                                parents = DataBaseManager.getInstance().getAllParents();
                                Snackbar snackbar = Snackbar
                                        .make(v, "Parent Added", Snackbar.LENGTH_LONG);
                                snackbar.show();
                                Intent intent = new Intent(UserRegister.this, AddChildrenToNewParent.class);
                                intent.putExtra("userID", parents.get(parents.size() - 1).getId());
                                startActivity(intent);
                            } else {
                                Toast.makeText(UserRegister.this, task.getException().toString(), Toast.LENGTH_LONG);
                            }
                        }
                    });
                }

            }
        });

        ImageView btnBack = findViewById(R.id.btnBack);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AddUser.this, MainActivity.class);
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