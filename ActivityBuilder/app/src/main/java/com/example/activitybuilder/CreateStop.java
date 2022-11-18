package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.activitybuilder.model.Stop;

public class CreateStop extends AppCompatActivity {
    long eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_stop);

        Intent intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);
    }

    public void finish(View view) {
        this.finish();
    }

    public void pairNFC(View view) {
        startActivity(new Intent(CreateStop.this, PairNfcTag.class));
    }

    public void save(View view){
        EditText location_name = findViewById(R.id.locationField);
        String location = ((TextView) this.findViewById(R.id.locationField)).getText().toString();


        if (location.length() == 0) {
            location_name.setError("This field is required");
        } else{
            String content = ((TextView) this.findViewById(R.id.contentField)).getText().toString();
            System.out.println("About to create stop with eventId " + eventId);
            Stop stop = Stop.createStop(getApplicationContext(),eventId,content,location);

            Intent intent = new Intent(CreateStop.this, ManageStops.class);
            intent.putExtra("event_id", eventId);
            startActivity(intent);
        }


    }
}