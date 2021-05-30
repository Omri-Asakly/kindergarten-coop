package com.uni.haifa.kgco_op;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditParent extends AppCompatActivity {

    private ListView userListView;
    private UserListAdapter listAdapter;
    private ChildrenAdapter childrenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_parent);
        List<Parent> dataList = new ArrayList<>();
        userListView = (ListView) findViewById(R.id.userListView);
//        info = (ListView) findViewById(R.id.parentInfo);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        for(Parent p : parents)
            dataList.add(p);
        Context c=this;
        listAdapter = new UserListAdapter(this, dataList);
        userListView.setAdapter(listAdapter);


        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditParent.this, MainPage.class);
                startActivity(intent);
            }
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Parent selectedParent = listAdapter.getItem(position);

                //fill details
                EditText name=findViewById(R.id.name);
                EditText email=findViewById(R.id.email);
                EditText password=findViewById(R.id.password);
                EditText date=findViewById(R.id.date);
                name.setText(selectedParent.getUserName());
                email.setText(selectedParent.getEmail());
                password.setText(selectedParent.getPassword());
                date.setText(selectedParent.getLicenseDate().toString());

                Button deleteUser=findViewById(R.id.deleteUser);
                deleteUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataBaseManager.getInstance().deleteParent(selectedParent);
                        Toast.makeText(EditParent.this,"User Deleted" ,Toast.LENGTH_LONG).show();
                        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
                        for(Parent p : parents)
                            dataList.add(p);

                        listAdapter = new UserListAdapter(c, dataList);
                        userListView.setAdapter(listAdapter);
                    }
                });

                Button updateUser=findViewById(R.id.updateUser);
                updateUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                        String n = name.getText().toString();
                        String e = email.getText().toString();
                        String p =  password.getText().toString();
                        Date d = null;
                        try {
                            d = formatter.parse(date.getText().toString());
                        } catch (ParseException error) {
                            error.printStackTrace();
                        }
                        Parent parent = new Parent(selectedParent.getId(),n, e, p, d);
                        DataBaseManager.getInstance().updateParent(parent);
                        Toast.makeText(EditParent.this,"User Updated" ,Toast.LENGTH_LONG).show();
                        userListView.setAdapter(null);
                        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
                        for(Parent parent1 : parents)
                            dataList.add(parent1);

                        listAdapter = new UserListAdapter(c, dataList);
                        userListView.setAdapter(listAdapter);
                    }
                });
//                List<Child> selectedChildren = new ArrayList<>();
//                List<Child> children = DataBaseManager.getInstance().getAllChildren();
//                for(Child c : children){
//                    if(c.getParentId() == selectedParent.getId()){
//                        selectedChildren.add(c);
//                    }
//                }
//                childrenAdapter = new ChildrenAdapter(EditParent.this, selectedChildren);
//                info.setAdapter(childrenAdapter);
            }
        });
    }
}