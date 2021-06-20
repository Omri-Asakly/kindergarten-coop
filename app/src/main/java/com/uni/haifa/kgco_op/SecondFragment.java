package com.uni.haifa.kgco_op;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(container.getContext(), (CharSequence) currentUser,Toast.LENGTH_LONG);
        EditText un = (EditText) view.findViewById(R.id.accountEmail);
        un.setText(currentUser.getEmail());

        return view;
    }

}
