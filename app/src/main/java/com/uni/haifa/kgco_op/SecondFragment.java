package com.uni.haifa.kgco_op;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_second, container, false);

        List<Parent> parents=DataBaseManager.getInstance().getAllParents();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        EditText un = (EditText) view.findViewById(R.id.accountName);
        EditText en = (EditText) view.findViewById(R.id.accountEmail);
        EditText ln = (EditText) view.findViewById(R.id.accountLicense);
        Parent user=null;
        if(currentUser!=null) {
            for (Parent p : parents)
                if (p.getEmail().equals(currentUser.getEmail())) {
                    user = p;

                }

            if (user != null) {
                un.setText(user.getUserName());
                en.setText(user.getEmail());
                ln.setText(user.getLicenseDate().toString());
            }
        }else{
            un.setText("ADMIN");
            en.setText("ADMIN@ADMIN.COM");
            ln.setText("12-12-2100");
        }

        Button logout=view.findViewById(R.id.btnOut);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseAuth.getInstance().signOut();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                }).create().show();
            }
        });


        return view;
    }

}
