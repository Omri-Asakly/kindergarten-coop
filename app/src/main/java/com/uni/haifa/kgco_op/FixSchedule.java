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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FixSchedule extends AppCompatActivity {
    private List<Parent> parents;
    private HashMap<Integer, String> namesMap;
    private List<String> names;
    private ImageButton morningImg;
    private ImageButton eveningImg;
    private boolean selectedImg;
    private AutoCompleteTextView morningTxt;
    private AutoCompleteTextView eveningTxt;
    private Button addScheduleBtn;
    //popup
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ListView listView;
    private UserListAdapter listAdapter;
    private TextView selected;
    private Parent selectedParent;
    private Button selectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_schedule);
        Bundle b = getIntent().getExtras();
        morningImg = (ImageButton) findViewById(R.id.morningImg);
        eveningImg = (ImageButton) findViewById(R.id.eveningImg);

        String value = b.getString("date");
        TextView dateTxt = findViewById(R.id.dateTxt);
        dateTxt.setText(value);
        parents = DataBaseManager.getInstance().getAllParents();
        autoFill();
        morningImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = true;
                selectParentDialog();
            }
        });

        eveningImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg = false;
                selectParentDialog();
            }
        });

        addScheduleBtn = (Button) findViewById(R.id.addScheduleBtn);
        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Schedule schedule = null;
                try {
                    schedule = new Schedule(morningTxt.getText().toString(), eveningTxt.getText().toString(), value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                List<Schedule> schedules = DataBaseManager.getInstance().getAllSchedules();
                Date date = null;
                try {
                    date = Schedule.stringToDate(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                boolean flag = true;
                for(Schedule s : schedules){
                    if(s.compare(date)){
                        flag = false;
                        break;
                    }
                }
                if(flag) {
                    DataBaseManager.getInstance().createSchedule(schedule);
                    Intent intent = new Intent(FixSchedule.this, weeklySchedule.class);
                    startActivity(intent);
                }
                else{
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, names.toArray(new String[names.size()]));
        adapter.notifyDataSetChanged();
        morningTxt = (AutoCompleteTextView)
                findViewById(R.id.morningTxt);
        morningTxt.setAdapter(adapter);

        eveningTxt = (AutoCompleteTextView)
                findViewById(R.id.eveningTxt);
        eveningTxt.setAdapter(adapter);
    }

    private void autoFill() {
        namesMap = new HashMap<>();
        names = new ArrayList<String>();
        for(Parent p : parents) {
            namesMap.put(p.getId(), p.getUserName());
            names.add(p.getUserName());
        }
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
                    morningTxt.setText(selectedParent.getUserName());
                } else{
                    eveningTxt.setText(selectedParent.getUserName());
                }
                dialog.hide();
            }
        });
        dialogBuilder.setView(parentPopupView);
        dialog = dialogBuilder.create();
        dialog.show();;
    }

}

