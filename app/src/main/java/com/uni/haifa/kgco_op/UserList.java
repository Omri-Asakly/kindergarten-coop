package com.uni.haifa.kgco_op;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private ListView userListView;
    private UserListAdapter listAdapter;
    private ChildrenAdapter childrenAdapter;
    private ListView info;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        context = this;
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Users");
        ab.setDisplayHomeAsUpEnabled(true);
        DataBaseManager.getInstance().openDataBase(this);
        List<Parent> dataList = new ArrayList<>();
        userListView = (ListView) findViewById(R.id.userListView);
        info = (ListView) findViewById(R.id.parentInfo);
        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        for(Parent p : parents)
            dataList.add(p);


        listAdapter = new UserListAdapter(this, dataList);
        userListView.setAdapter(listAdapter);

        // listener to display children of selected parent
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Parent selectedParent = listAdapter.getItem(position);

                List<Child> selectedChildren = new ArrayList<>();
                List<Child> children = DataBaseManager.getInstance().getAllChildren();
                for(Child c : children){
                    if(c.getParentId() == selectedParent.getId()){
                        selectedChildren.add(c);
                    }
                }
                childrenAdapter = new ChildrenAdapter(UserList.this, selectedChildren);
                info.setAdapter(childrenAdapter);
            }
        });


        // update/sync data from fireStore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("People");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(context, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    DataBaseManager.getInstance().removeAllParents();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Parent parent = document.toObject(Parent.class);
                        DataBaseManager.getInstance().createParent(parent);

                    }

                    List<Parent> parentsList = DataBaseManager.getInstance().getAllParents();
                    listAdapter = new UserListAdapter(UserList.this, parentsList);
                    userListView.setAdapter(listAdapter);

                } else {
                    Toast.makeText(context, "Current data: null",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

        FirebaseFirestore dbC = FirebaseFirestore.getInstance();
        CollectionReference collRefC = dbC.collection("child");

        collRefC.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(context, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    DataBaseManager.getInstance().removeAllChildren();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Child child = document.toObject(Child.class);
                        DataBaseManager.getInstance().createChild(child);

                    }

                    List<Child> childrenList = DataBaseManager.getInstance().getAllChildren();
                    childrenAdapter = new ChildrenAdapter(UserList.this, childrenList);
                    info.setAdapter(childrenAdapter);

                } else {
                    Toast.makeText(context, "Current data: null",
                            Toast.LENGTH_LONG).show();

                }
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