package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.activitybuilder.model.Event;

public class EditEvent extends AppCompatActivity {
    long eventId;
    String eventName;
    String eventSL;
    int eventDuration;
    String eventDate;
    String eventDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        Intent intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);
        eventName = intent.getStringExtra("event_name");
        eventSL = intent.getStringExtra("event_SL");
        eventDuration = intent.getIntExtra("event_duration", 0);
        eventDate = intent.getStringExtra("event_date");
        eventDescription = intent.getStringExtra("event_description");

        EditText name = (EditText) findViewById (R.id.eventNameField);
        name.setText(eventName);
        EditText sl = (EditText) findViewById (R.id.startingLocationField);
        sl.setText(eventSL);
        EditText duration = (EditText) findViewById (R.id.durationField);
        duration.setText(String.valueOf(eventDuration));
       // EditText date = (EditText) findViewById (R.id.dateField);
        //date.setText(eventDate);
        EditText description = (EditText) findViewById (R.id.descriptionField);
        description.setText(eventDescription);

    }

    public void finish(View view) {
        this.finish();
    }

    public void save(View view){
        String eventName = ((TextView) this.findViewById(R.id.eventNameField)).getText().toString();
        String date = ((TextView) this.findViewById(R.id.editTextDate)).getText().toString();
        String durationValue = ((TextView) this.findViewById(R.id.durationField)).getText().toString();
        int duration = Integer.parseInt(durationValue);
        String startingLocation = ((TextView) this.findViewById(R.id.startingLocationField)).getText().toString();
        String description = ((TextView) this.findViewById(R.id.descriptionField)).getText().toString();


        Intent intent = getIntent();
        eventId = intent.getLongExtra("event_id", 0);

        System.out.println(eventId);
        Event event = Event.updateEvent(getApplicationContext(),eventId,eventName,date,duration,startingLocation,description);
        System.out.println("Created event with ID " + event.getEventId());

        Intent new_intent = new Intent(EditEvent.this, ManageStops.class);
        intent.putExtra("event_id", event.getEventId());
        intent.putExtra("event_name", event.getName());
        intent.putExtra("event_SL", event.getStartingLocation());
        intent.putExtra("event_duration", event.getDuration());
        intent.putExtra("event_date", event.getDate());
        intent.putExtra("event_description", event.getDescription());
        startActivity(new_intent);
    }
    public void createStop(View view) {
        Intent new_intent = new Intent(EditEvent.this, CreateStop.class);
        new_intent.putExtra("event_id", eventId);
        startActivity(new_intent);

    }
}