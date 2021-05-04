package com.uni.haifa.kgco_op;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<Parent> parents = new ArrayList<>();
    ArrayList<Child> children = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReadParentsFromJSON();
        ReadChildrenFromJSON();
        Button btnDM = findViewById(R.id.btnDM);

        final SharedPreferences appSettingPrefs= getSharedPreferences("AppSettingPrefs",  0);
        final SharedPreferences.Editor sharedPrefsEdit = appSettingPrefs.edit();
        final Boolean isNightModeOn = appSettingPrefs.getBoolean("NightMode", false);
        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btnDM.setText("Disable Dark Mode");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btnDM.setText("Enable Dark Mode");
        }
        btnDM.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNightModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPrefsEdit.putBoolean("NightMode", false);
                    sharedPrefsEdit.apply();

                    btnDM.setText("Enable Dark Mode");
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPrefsEdit.putBoolean("NightMode", true);
                    sharedPrefsEdit.apply();

                    btnDM.setText("Disable Dark Mode");
                }
            }
        });
        Button loginBtn= this.<Button>findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                EditText editTextEmail=(EditText)findViewById(R.id.editTextEmail);
                String email=editTextEmail.getText().toString();
                EditText editTextPassword=(EditText)findViewById(R.id.editTextPassword);
                String password=editTextPassword.getText().toString();
                if ((email.equals("admin")) && (password.equals("admin"))){
                    Toast.makeText(getApplicationContext(),"Hello Admin",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent);
                }
                else{
     //              Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),password ,Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, MainPage.class);
                    startActivity(intent);
                }
            }

        });

    }

    private void ReadParentsFromJSON(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset("parent"));
            JSONArray jsonArray = jsonObject.getJSONArray("parents");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject parentData = jsonArray.getJSONObject(i);
                Date date = formatter.parse(parentData.getString("licenseDate"));
                Parent parent = new Parent(Integer.parseInt(parentData.getString("id")), parentData.getString("userName"), parentData.getString("email"), parentData.getString("password"), date);
                parents.add(parent);
            }
            Global.getInstance().setParents(parents);;
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void ReadChildrenFromJSON(){
        try{
            JSONObject jsonObject = new JSONObject(JsonDataFromAsset("child"));
            JSONArray jsonArray = jsonObject.getJSONArray("children");
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject childData = jsonArray.getJSONObject(i);
                Child child = new Child(Integer.parseInt(childData.getString("id")), Integer.parseInt(childData.getString("parentID")), childData.getString("name"));
                children.add(child);
            }
            Global.getInstance().setChildren(children);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }


    private String JsonDataFromAsset(String path) {
        String json = null;
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.parent);
            int sizeOfFile = inputStream.available();
            byte[] bufferData = new byte[sizeOfFile];
            inputStream.read(bufferData);
            inputStream.close();
            json = new String(bufferData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}