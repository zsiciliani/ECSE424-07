package com.example.activitybuilder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.activitybuilder.model.Event;
import com.example.activitybuilder.model.Stop;

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
        System.out.println("Updated event with ID " + event.getEventId());

        Intent new_intent = new Intent(EditEvent.this, MainActivity.class);
        new_intent.putExtra("event_id", event.getEventId());
        new_intent.putExtra("event_name", event.getName());
        new_intent.putExtra("event_SL", event.getStartingLocation());
        new_intent.putExtra("event_duration", event.getDuration());
        new_intent.putExtra("event_date", event.getDate());
        new_intent.putExtra("event_description", event.getDescription());
        startActivity(new_intent);
    }
    public void createStop(View view) {
        Intent new_intent = new Intent(EditEvent.this, ManageStops.class);
        System.out.println("About to manage stops for eventId " + eventId);
        new_intent.putExtra("event_id", eventId);
        startActivity(new_intent);
    }

    public void delete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Stop?");
        builder.setMessage("Are you sure you would like to delete this stop?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Event.deleteEventById(getApplicationContext(),eventId);
                        startActivity(new Intent(EditEvent.this, MainActivity.class));
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void returnHome(View view) {
        startActivity(new Intent(EditEvent.this, MainActivity.class));
    }
}