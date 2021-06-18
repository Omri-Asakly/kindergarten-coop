package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddChildrenToNewParent extends AppCompatActivity {
    TextView txt;
    TextInputLayout textInputLayout;
    List<Child> children;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_children_to_new_parent);
        DataBaseManager.getInstance().openDataBase(this);
        children = DataBaseManager.getInstance().getAllChildren();
        txt = findViewById(R.id.textView);
        textInputLayout = findViewById(R.id.textInputLayout);
        Button save = findViewById(R.id.addBtn);
        Bundle b = getIntent().getExtras();
        int value = b.getInt("userID");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectNames(value);
                Snackbar snackbar = Snackbar
                        .make(v, "Children Inserted", Snackbar.LENGTH_SHORT).setAction("Show", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                Snackbar snackbar1 = Snackbar.make(v, "Message is restored!", Snackbar.LENGTH_SHORT);
//                                snackbar1.show();
                                Intent intent = new Intent(AddChildrenToNewParent.this, MainPage.class);
                                startActivity(intent);
                            }
                        });

                snackbar.show();
                snackbar.addCallback(new Snackbar.Callback() {

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        if (event == BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_TIMEOUT) {
                            Intent intent = new Intent(AddChildrenToNewParent.this, MainPage.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
        Parent parent = null;
        List<Parent> parents = DataBaseManager.getInstance().getAllParents();
        for (Parent p : parents) {
            if (value == p.getId()) {
                parent = p;
            }
        }
        try {
            txt.setText(parent.getUserName());
        } catch (NullPointerException e) {
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

    private void collectNames(int value) {
        String input = textInputLayout.getEditText().getText().toString();
        input = input.replaceAll("\\s", "");
        String[] names = input.split(",");
        for (String s : names) {
            Child child = new Child(value, s);
            DataBaseManager.getInstance().createChild(child);
        }

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