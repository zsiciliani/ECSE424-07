package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UnsuccessfulPair extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unsuccessful_pair);
    }

    public void finish(View view) {
        this.finish();
    }

    public void cancel(View view) {
        startActivity(new Intent(UnsuccessfulPair.this, CreateStop.class));
    }

}