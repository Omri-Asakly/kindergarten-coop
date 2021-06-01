package com.uni.haifa.kgco_op;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

public class Delete extends AppCompatActivity {

    private ImageButton firstImgM;
    private AutoCompleteTextView userDelete;
    private ImageButton childImgM;
    private AutoCompleteTextView childDelete;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ListView listView;
    private UserListAdapter listAdapter;
    private ChildrenAdapter childrenAdapter;
    private TextView selected;
    private Parent selectedParent;
    private Child selectedChild;
    private Button selectBtn;
    private boolean selectedImg;
    private HashMap<Integer, String> namesMap;
    private List<String> names;
    private List<Child> children;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        userDelete = (AutoCompleteTextView) findViewById(R.id.userDelete);
        childDelete = (AutoCompleteTextView) findViewById(R.id.childDelete);
        firstImgM = findViewById(R.id.firstImgM);
        childImgM = findViewById(R.id.childImgM);

        ImageView back = findViewById(R.id.backBtnDel);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delete.this, MainPage.class);
                startActivity(intent);

            }
        });

        Button del = findViewById(R.id.deleteUserBtn);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Child c : DataBaseManager.getInstance().getAllChildren()){
                    if(c.getParentId() == selectedParent.getId())
                        DataBaseManager.getInstance().deleteChild(c);
                }
                DataBaseManager.getInstance().deleteParent(selectedParent);
                userDelete.setText("");
                Toast.makeText(Delete.this, "User Deleted", Toast.LENGTH_LONG).show();
            }
        });
        Button delChild = findViewById(R.id.deleteChildBtn);
        delChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseManager.getInstance().deleteChild(selectedChild);
                Toast.makeText(Delete.this, "Child Deleted", Toast.LENGTH_LONG).show();
                childDelete.setText("");
            }
        });
        firstImgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = true;
                selectParentDialog();
            }
        });
        childImgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = false;
                selectParentDialog();
            }
        });
    }

    private void selectParentDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View parentPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        listView = (ListView) parentPopupView.findViewById(R.id.parentsView);
        selected = (TextView) parentPopupView.findViewById(R.id.selectedTxt);
        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        listAdapter = new UserListAdapter(this, parents);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedParent = listAdapter.getItem(position);
                selected.setText(selectedParent.getUserName());
            }
        });
        if (!selectedImg) {
            List<Child> children = DataBaseManager.getInstance().getAllChildren();
            childrenAdapter = new ChildrenAdapter(this, children);
            listView.setAdapter(childrenAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> child, View v, int position, long id) {
                    selectedChild = childrenAdapter.getItem(position);
                    selected.setText(selectedChild.getName());
                }
            });
        }
        selectBtn = (Button) parentPopupView.findViewById(R.id.confirmBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImg) {
                    userDelete.setText(selectedParent.getUserName());
                } else {
                    childDelete.setText(selectedChild.getName());
                }
                dialog.hide();
            }
        });
        dialogBuilder.setView(parentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private boolean isUser(List<Parent> list, String parent) {
        for (Parent p : list) {
            if (p.getUserName().equals(parent))
                return true;
        }
        Toast.makeText(this, parent + " doesn't exist", Toast.LENGTH_LONG);
        return false;
    }
}