package com.uni.haifa.kgco_op;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Delete extends AppCompatActivity {

    private ImageButton firstImgM;
    private AutoCompleteTextView userDelete;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ListView listView;
    private UserListAdapter listAdapter;
    private TextView selected;
    private Parent selectedParent;
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
        firstImgM = findViewById(R.id.firstImgM);

        Button del=findViewById(R.id.deleteUserBtn);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DataBaseManager.getInstance().deleteParent(selectedParent);
                Toast.makeText(Delete.this,"User Deleted" ,Toast.LENGTH_LONG).show();

            }
        });
        firstImgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = true;
                selectParentDialog();
            }
        });
    }

    private void selectParentDialog(){
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

        selectBtn = (Button) parentPopupView.findViewById(R.id.confirmBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImg){
                    userDelete.setText(selectedParent.getUserName());
                } else{
                    //scheduleDelete.setText(selectedParent.getUserName());
                }
                dialog.hide();
            }
        });
        dialogBuilder.setView(parentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();;
    }

    private boolean isUser(List<Parent> list, String parent){
        for(Parent p : list){
            if(p.getUserName().equals(parent))
                return true;
        }
        Toast.makeText(this, parent + " doesn't exist", Toast.LENGTH_LONG);
        return false;
    }
}