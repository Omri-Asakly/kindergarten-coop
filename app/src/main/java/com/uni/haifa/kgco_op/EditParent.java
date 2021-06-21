package com.uni.haifa.kgco_op;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
        ActionBar ab = getSupportActionBar();
        DataBaseManager.getInstance().openDataBase(this);
        ab.setTitle("Edit/Update");
        ab.setDisplayHomeAsUpEnabled(true);
        List<Parent> dataList = new ArrayList<>();
        userListView = (ListView) findViewById(R.id.userListView);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Context mc = this;
        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        for (Parent p : parents)
            dataList.add(p);
        Context c = this;
        listAdapter = new UserListAdapter(this, dataList);
        userListView.setAdapter(listAdapter);


        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Parent selectedParent = listAdapter.getItem(position);

                //fill details
                EditText name = findViewById(R.id.name);
                EditText email = findViewById(R.id.email);
                EditText password = findViewById(R.id.password);
                EditText date = findViewById(R.id.date);
                name.setText(selectedParent.getUserName());
                email.setText(selectedParent.getEmail());
                password.setText(selectedParent.getPassword());
                date.setText(selectedParent.getLicenseDate().toString());
                Button deleteUser = findViewById(R.id.deleteUser);
                deleteUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(mc)
                                .setTitle("Delete")
                                .setMessage("Are you sure you want to Delete " + selectedParent.getUserName() + "?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do something
                                                Snackbar snackbar = Snackbar
                                                        .make(v, "Deleting Parent", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Snackbar snackbar1 = Snackbar.make(v, "Delete Canceled", Snackbar.LENGTH_SHORT);
                                                                snackbar1.show();

                                                            }
                                                        });

                                                snackbar.show();
                                                snackbar.addCallback(new Snackbar.Callback() {

                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {
                                                        if (event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT) {
                                                            DataBaseManager.getInstance().deleteParent(selectedParent);
                                                            List<Parent> parents = DataBaseManager.getInstance().getAllParents();
                                                            dataList.removeAll(dataList);
                                                            for (Parent p : parents)
                                                                dataList.add(p);

                                                            listAdapter = new UserListAdapter(c, dataList);
                                                            userListView.setAdapter(listAdapter);
                                                        }
                                                    }
                                                });
                                            }
                                        }).create().show();
                    }
                });

                Button updateUser = findViewById(R.id.updateUser);
                updateUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
                        String n = name.getText().toString();
                        String e = email.getText().toString();
                        String p = password.getText().toString();
                        Date d = null;
                        try {
                            d = formatter.parse(date.getText().toString());
                        } catch (ParseException error) {
                            error.printStackTrace();
                        }
                        Parent parent = new Parent(selectedParent.getId(), n, e, p, d);
                        new AlertDialog.Builder(mc)
                                .setTitle("Update")
                                .setMessage("Are you sure you want to Update " + selectedParent.getUserName() + "?")
                                .setNegativeButton(android.R.string.no, null)
                                .setPositiveButton(android.R.string.yes,
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do something
                                                Snackbar snackbar = Snackbar
                                                        .make(v, "Updating Parent", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Snackbar snackbar1 = Snackbar.make(v, "Update Canceled", Snackbar.LENGTH_SHORT);
                                                                snackbar1.show();

                                                            }
                                                        });

                                                snackbar.show();
                                                snackbar.addCallback(new Snackbar.Callback() {

                                                    @Override
                                                    public void onDismissed(Snackbar snackbar, int event) {
                                                        if (event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT) {
                                                            DataBaseManager.getInstance().updateParent(parent);
                                                            Toast.makeText(EditParent.this, "User Updated", Toast.LENGTH_LONG).show();
                                                            userListView.setAdapter(null);
                                                            List<Parent> parents = DataBaseManager.getInstance().getAllParents();
                                                            for (Parent parent1 : parents)
                                                                dataList.add(parent1);

                                                            listAdapter = new UserListAdapter(c, dataList);
                                                            userListView.setAdapter(listAdapter);
                                                        }

                                                    }
                                                });
                                            }
                                        }).create().show();
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