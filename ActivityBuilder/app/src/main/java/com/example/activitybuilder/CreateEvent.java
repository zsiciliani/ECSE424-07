package com.example.activitybuilder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        Event event = Event.createEvent(getApplicationContext(),"event1","01/20/10",12,"McConnell","description");
        System.out.println("Created event with ID " + event.getEventId());
        Intent intent = new Intent(CreateEvent.this, ManageStops.class);
        intent.putExtra("event_id", event.getEventId());
        startActivity(intent);
    }

}