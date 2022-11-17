package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void manageEvent(View view) {
        startActivity(new Intent(MainActivity.this, ManageEvents.class));
    }

    public void help(View view) {
        startActivity(new Intent(MainActivity.this, Help.class));
    }

    public void cloneEvent(View view) {
        startActivity(new Intent(MainActivity.this, CloneEvent.class));
    }

    public void createEvent(View view) {
        startActivity(new Intent(MainActivity.this, CreateEvent.class));
    }


}