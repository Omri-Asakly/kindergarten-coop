package com.uni.haifa.kgco_op;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class tripInfo extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<>();
    Button parse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_info);
        parse = findViewById(R.id.button_parse);
        parse.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                jsonParse(getApplicationContext());
            }
        });


    }
    private String jsonParse(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("child");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonarray = new JSONArray(json);

            for(int i = 0; i < jsonarray.length(); i++){
                Toast.makeText(getApplicationContext(), jsonarray.get(i).toString(), Toast.LENGTH_SHORT).show();
            }


        } catch (IOException | JSONException ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

            return null;
        }
        return json;
    }
}