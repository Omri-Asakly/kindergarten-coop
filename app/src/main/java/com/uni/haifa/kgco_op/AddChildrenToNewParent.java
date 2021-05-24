package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddChildrenToNewParent extends AppCompatActivity {
    TextView txt;
    TextInputLayout textInputLayout;
    List<Child> children = DataBaseManager.getInstance().getAllChildren();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_children_to_new_parent);
        txt = findViewById(R.id.textView);
        textInputLayout = findViewById(R.id.textInputLayout);
        Button save = findViewById(R.id.addBtn);
        Bundle b = getIntent().getExtras();
        int value = b.getInt("userID");
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                collectNames(value);
                Intent intent = new Intent(AddChildrenToNewParent.this, MainPage.class);
                startActivity(intent);
            }
        });
        Parent parent = null;
        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        for(Parent p : parents){
            if(value == p.getId()){
                parent = p;
            }
        }
        try {
            txt.setText(parent.getUserName());
        } catch(NullPointerException e){
            e.getStackTrace();
        }

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddChildrenToNewParent.this, AddUser.class);
                startActivity(intent);
            }
        });
    }

    private void collectNames(int value){
        int count = 1;
        String input = textInputLayout.getEditText().getText().toString();
        input.replaceAll("\\s+","");
        String[] names = input.split(",");
        for(String s : names){
            Child child = new Child(children.size()+count, value, s);
            count++;
            DataBaseManager.getInstance().createChild(child);
        }
        for(Child c : DataBaseManager.getInstance().getAllChildren())
            System.out.println(c.toString());
    }

}