package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.activitybuilder.model.Stop;

public class CreateStop extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stop);

        Intent intent = getIntent();
        id = intent.getIntExtra("event_id", 0);
    }

    public void finish(View view) {
        this.finish();
    }

    public void pairNFC(View view) {
        startActivity(new Intent(CreateStop.this, PairNfcTag.class));
    }

    public void save(View view){
        String location = ((TextView) this.findViewById(R.id.locationField)).getText().toString();
        String content = ((TextView) this.findViewById(R.id.contentField)).getText().toString();
        Stop stop = Stop.createStop(getApplicationContext(),id,content,location);
    }
}