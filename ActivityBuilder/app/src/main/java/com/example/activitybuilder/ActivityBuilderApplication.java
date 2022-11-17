package com.example.activitybuilder;

import android.app.Application;

import com.example.activitybuilder.model.Event;

import java.util.Date;

//TODO now that I'm adding a database I'm not sure if this class is necessary anymore
public class ActivityBuilderApplication extends Application {
    public Event createEvent(String name, String date, int duration, String startingLocation, String description) {
        Event event = new Event();
        event.setName(name);
        event.setDate(date);
        event.setDuration(duration);
        event.setStartingLocation(startingLocation);
        event.setDescription(description);
        return event;
    }
}
