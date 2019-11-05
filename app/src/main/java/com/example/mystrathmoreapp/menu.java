package com.example.mystrathmoreapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import static android.content.Context.MODE_PRIVATE;

public class menu extends AppCompatActivity {
    public TextView mUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mUsername = findViewById(R.id.username_textview);

        //Call for SharedPreference
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_info", MODE_PRIVATE);
        String user = sharedPreferences.getString("FirstName", "");
        mUsername.setText("Welcome "+user);



    }

    public void timetable_clk(View view){
        Intent i = new Intent(this,timetable.class);
        startActivity(i);
    }

    public void profile_clk(View view){
        Intent i = new Intent(this, profile.class);
        startActivity(i);
    }

    public void updates_clk(View view){
        Intent i = new Intent(this,updatesActivity.class);
        startActivity(i);
    }
}
