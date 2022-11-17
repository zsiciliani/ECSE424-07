package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.activitybuilder.model.Event;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void finish(View view) {
        this.finish();
    }

    public void manageStops(View view) {
        String eventName = ((TextView) this.findViewById(R.id.eventNameField)).getText().toString();
        String date = ((TextView) this.findViewById(R.id.dateField)).getText().toString();
        String durationValue = ((TextView) this.findViewById(R.id.durationField)).getText().toString();
        int duration = Integer.parseInt(durationValue);
        String startingLocation = ((TextView) this.findViewById(R.id.startingLocationField)).getText().toString();
        String description = ((TextView) this.findViewById(R.id.descriptionField)).getText().toString();

        Event event = Event.createEvent(getApplicationContext(),eventName,date,duration,startingLocation,description);
        System.out.println("Created event with ID " + event.getEventId());
        Intent intent = new Intent(CreateEvent.this, ManageStops.class);
        intent.putExtra("event_id", event.getEventId());
        startActivity(intent);
    }


}