package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddChildrenToTrip extends AppCompatActivity {
    private TextView dateTxt;
    private AutoCompleteTextView firstTxtM;
    private AutoCompleteTextView secondTxtM;
    private AutoCompleteTextView thirdTxtM;
    private Button addScheduleBtn;
    private ImageButton firstImgM;
    private ImageButton secondImgM;
    private ImageButton thirdImgM;
    private HashMap<Integer, String> namesMap;
    private List<String> names;
    private List<Child> children;
    private AutoCompleteTextView firstTxtE;
    private AutoCompleteTextView secondTxtE;
    private AutoCompleteTextView thirdTxtE;
    private ImageButton firstImgE;
    private ImageButton secondImgE;
    private ImageButton thirdImgE;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ListView listView;
    private ChildrenAdapter listAdapter;
    private TextView selected;
    private Child selectedChild;
    private Button selectBtn;
    private int selectedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_children_to_trip);

        Bundle b = getIntent().getExtras();
        String value = b.getString("date");
        System.out.println(value);
        dateTxt = findViewById(R.id.dateTxt);
        dateTxt.setText("Children for date " + value);

        firstTxtM = (AutoCompleteTextView) findViewById(R.id.firstTxtM);
        secondTxtM = (AutoCompleteTextView) findViewById(R.id.secondTxtM);
        thirdTxtM = (AutoCompleteTextView) findViewById(R.id.thirdTxtM);

        addScheduleBtn = findViewById(R.id.addScheduleBtn);
        firstImgM = findViewById(R.id.firstImgM);
        secondImgM = findViewById(R.id.secondImgM);
        thirdImgM = findViewById(R.id.thirdImgM);

        firstTxtE = (AutoCompleteTextView) findViewById(R.id.firstTxtE);
        secondTxtE = (AutoCompleteTextView) findViewById(R.id.secondTxtE);
        thirdTxtE = (AutoCompleteTextView) findViewById(R.id.thirdTxtE);

        firstImgE = findViewById(R.id.firstImgE);
        secondImgE = findViewById(R.id.secondImgE);
        thirdImgE = findViewById(R.id.thirdImgE);


        children = DataBaseManager.getInstance().getAllChildren();
        autoFill();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, names.toArray(new String[names.size()]));
        adapter.notifyDataSetChanged();

        firstTxtE.setAdapter(adapter);
        firstTxtM.setAdapter(adapter);
        secondTxtE.setAdapter(adapter);
        secondTxtM.setAdapter(adapter);
        thirdTxtE.setAdapter(adapter);
        thirdTxtM.setAdapter(adapter);


        firstImgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 0;
                selectParentDialog();
            }
        });

        secondImgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 1;
                selectParentDialog();
            }
        });

        thirdImgM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 2;
                selectParentDialog();
            }
        });

        firstImgE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 3;
                selectParentDialog();
            }
        });

        secondImgE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 4;
                selectParentDialog();
            }
        });

        thirdImgE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = 5;
                selectParentDialog();
            }
        });

        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String morning = "";
                String evening = "";

                morning = firstTxtM.getText().toString() + ", " + secondTxtM.getText().toString() + ", " + thirdTxtM.getText().toString();
                evening = firstTxtE.getText().toString() + ", " + secondTxtE.getText().toString() + ", " + thirdTxtE.getText().toString();
                try {
                    Schedule schedule = new Schedule(b.getString("morning"), b.getString("evening"), value, morning, evening);
                    DataBaseManager.getInstance().createSchedule(schedule);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(AddChildrenToTrip.this, weeklySchedule.class);
                startActivity(intent);
            }
        });
    }

    private void selectParentDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View parentPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        listView = (ListView) parentPopupView.findViewById(R.id.parentsView);
        selected = (TextView) parentPopupView.findViewById(R.id.selectedTxt);
        List<Child> children = DataBaseManager.getInstance().getAllChildren();
        listAdapter = new ChildrenAdapter(this, children);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedChild = listAdapter.getItem(position);
                selected.setText(selectedChild.getName());
            }
        });

        selectBtn = (Button) parentPopupView.findViewById(R.id.confirmBtn);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImg == 0) {
                    firstTxtM.setText(selectedChild.getName());
                } else if (selectedImg == 1) {
                    secondTxtM.setText(selectedChild.getName());
                } else if (selectedImg == 2){
                    thirdTxtM.setText(selectedChild.getName());
                } else if (selectedImg == 3){
                    firstTxtE.setText(selectedChild.getName());
                } else if (selectedImg == 4){
                    secondTxtE.setText(selectedChild.getName());
                } else if (selectedImg == 5){
                    thirdTxtE.setText(selectedChild.getName());
                }
                dialog.hide();
            }
        });
        dialogBuilder.setView(parentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    private void autoFill() {
        namesMap = new HashMap<>();
        names = new ArrayList<String>();
        for(Child c : children) {
            namesMap.put(c.getId(), c.getName());
            names.add(c.getName());
        }
    }
}