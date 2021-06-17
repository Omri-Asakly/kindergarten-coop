package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddUser extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView password;
    TextView date;
    List<Parent> parents = DataBaseManager.getInstance().getAllParents();
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseManager.getInstance().openDataBase(this);
        super.onCreate(savedInstanceState);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Insert Parent");
        ab.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_add_user);
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
                        Toast.makeText(AddUser.this,"Email already in use" ,Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if (flag) {
                    Date dateF = null;
                    try {
                        dateF = formatter.parse(date.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Parent parent = new Parent(name.getText().toString(), email.getText().toString(), password.getText().toString(), dateF);
                    DataBaseManager.getInstance().createParent(parent);
                    Snackbar snackbar = Snackbar
                            .make(v, "Parent Added", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    parents = DataBaseManager.getInstance().getAllParents();
                    Intent intent = new Intent(AddUser.this, AddChildrenToNewParent.class);
                    intent.putExtra("userID", parents.get(parents.size()-1).getId());
                    startActivity(intent);

                }
            }
        });

    }
}