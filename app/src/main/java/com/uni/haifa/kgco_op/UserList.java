package com.uni.haifa.kgco_op;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private ListView userListView;
    private UserListAdapter listAdapter;
    private ChildrenAdapter childrenAdapter;
    private ListView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        List<Parent> dataList = new ArrayList<>();
        userListView = (ListView) findViewById(R.id.userListView);
        info = (ListView) findViewById(R.id.parentInfo);
        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        for(Parent p : parents)
            dataList.add(p);

        listAdapter = new UserListAdapter(this, dataList);
        userListView.setAdapter(listAdapter);

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
    }
}