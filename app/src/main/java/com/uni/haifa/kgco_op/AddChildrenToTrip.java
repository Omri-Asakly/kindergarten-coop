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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

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
    private FirebaseAuth mAuth;
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
        DataBaseManager.getInstance().openDataBase(this);
        setContentView(R.layout.activity_add_children_to_trip);
        ActionBar ab=getSupportActionBar();
        ab.setTitle("Add Children To Shift");
        ab.setDisplayHomeAsUpEnabled(true);
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

        //adapter to display the children's names in autofill/sleect
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, names.toArray(new String[names.size()]));
        adapter.notifyDataSetChanged();

        firstTxtE.setAdapter(adapter);
        firstTxtM.setAdapter(adapter);
        secondTxtE.setAdapter(adapter);
        secondTxtM.setAdapter(adapter);
        thirdTxtE.setAdapter(adapter);
        thirdTxtM.setAdapter(adapter);

        // update/sync data from fireStore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("People");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(AddChildrenToTrip.this, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    DataBaseManager.getInstance().removeAllParents();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Parent parent = document.toObject(Parent.class);
                        DataBaseManager.getInstance().createParent(parent);

                    }

                } else {
                    Toast.makeText(AddChildrenToTrip.this, "Current data: null",
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
                    Toast.makeText(AddChildrenToTrip.this, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    DataBaseManager.getInstance().removeAllChildren();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Child child = document.toObject(Child.class);
                        DataBaseManager.getInstance().createChild(child);

                    }


                } else {
                    Toast.makeText(AddChildrenToTrip.this, "Current data: null",
                            Toast.LENGTH_LONG).show();

                }
            }
        });


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
                List<String> morning = new ArrayList<>();
                List<String> evening = new ArrayList<>();
                boolean flag  = (isUser(children, firstTxtM.getText().toString())
                 && isUser(children, secondTxtM.getText().toString())
                 && isUser(children, thirdTxtM.getText().toString())
                 && isUser(children, firstTxtE.getText().toString())
                 && isUser(children, secondTxtE.getText().toString())
                 && isUser(children, thirdTxtE.getText().toString()));
                //gather data from fields and create the schedule
                if(flag){
                    morning.add(firstTxtM.getText().toString());
                    morning.add(secondTxtM.getText().toString());
                    morning.add(thirdTxtM.getText().toString());
                    evening.add(firstTxtE.getText().toString());
                    evening.add(secondTxtE.getText().toString());
                    evening.add(thirdTxtE.getText().toString());
                    try {
                        Schedule schedule = new Schedule(b.getString("morning"), b.getString("evening"), value, morning, evening);
                        DataBaseManager.getInstance().createSchedule(schedule);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(AddChildrenToTrip.this, weeklySchedule.class);
                    startActivity(intent);
                } else{
                    System.out.println("na");
                }
            }
        });
    }

    private void selectParentDialog() {
        // choose image for field to get popup window
        // use it to select/autofill a parent
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
    //validate user
    private boolean isUser(List<Child> list, String child){
        for(Child c : list){
            if(c.getName().equals(child))
                return true;
        }
        Toast.makeText(this, child + " doesn't exist", Toast.LENGTH_LONG);
        return false;
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