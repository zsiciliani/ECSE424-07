package com.example.activitybuilder;

import android.app.Application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ActivityBuilderApplication extends Application {
    private HashMap<Integer, Event> eventMap;
    /** Event IDs do not need to be ordered so we don't care if some get deleted or skipped, but
     * they need to be unique
     */
    private int newEventId;

    public Event getEventById(int id) {
        return eventMap.get(id);
    }

    public Event createEvent(String name, Date date, int duration, String startingLocation, String description) {
        Event event = new Event();
        event.setName(name);
        event.setDate(date);
        event.setDuration(duration);
        event.setStartingLocation(startingLocation);
        event.setDescription(description);
        event.setId(this.newEventId);
        this.eventMap.put(newEventId, event);
        this.newEventId++;
        return event;
    }
}
