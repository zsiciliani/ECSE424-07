package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EditStop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stop);
    }

    public void finish(View view) {
        this.finish();
    }

    public void delete(View view) {
        startActivity(new Intent(EditStop.this, ManageStops.class));
    }

    public void pairNFC(View view) {
        startActivity(new Intent(EditStop.this, PairNfcTag.class));
    }
}