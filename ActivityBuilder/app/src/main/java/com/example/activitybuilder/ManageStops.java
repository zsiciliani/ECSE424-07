package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageStops extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_stops);

        Intent intent = getIntent();
        id = intent.getIntExtra("event_id", 0);
    }

    public void finish(View view) {
        this.finish();
    }

    public void addStop(View view){
        Intent intent = new Intent(ManageStops.this, CreateStop.class);
        intent.putExtra("event_id", id);
        startActivity(intent);
    }
}