package com.uni.haifa.kgco_op;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddUser extends AppCompatActivity {
    TextView name;
    TextView email;
    TextView password;
    TextView date;
    ArrayList<Parent> parents = Global.getInstance().getParents();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        name = findViewById(R.id.userTxt);
        email = findViewById(R.id.mailTxt);
        password = findViewById(R.id.passTxt);
        date = findViewById(R.id.dateTxt);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Button save = findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Date dateF = null;
                try {
                    dateF = formatter.parse(date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Parent parent = new Parent(parents.size()+1, name.getText().toString(), email.getText().toString(), password.getText().toString(), dateF);
                parents.add(parent);
                Global.getInstance().setParents(parents);
                for(Parent p : Global.getInstance().getParents())
                System.out.println(p.toString());
            }
        });


    }
}