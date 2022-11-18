package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SuccessfulPair extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_pair);
    }

    public void finish(View view) {
        this.finish();
    }

    public void okay(View view) {
        startActivity(new Intent(SuccessfulPair.this, MainActivity.class));
    }
}