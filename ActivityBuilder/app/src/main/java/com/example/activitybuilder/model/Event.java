package com.example.activitybuilder.model;

import android.content.Context;

import com.example.activitybuilder.AppDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {

    @PrimaryKey(autoGenerate = true)
    public long eventId;

    private String name;
    private String date;
    private int duration;
    private String startingLocation;
    private String description;
    //Might remove numStops and just do length of array in db instead of tracking this manually
    private int numStops;
    private boolean isOrdered;

    //////////////////////////////////////////////////////////////////////
    /////////////////Constructors, Getters and Setters////////////////////
    //////////////////////////////////////////////////////////////////////

    public Event() {}

    public Event(String name, String date, int duration, String startingLocation, String description) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.startingLocation = startingLocation;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStartingLocation() {
        return startingLocation;
    }

    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getNumStops() {
        return numStops;
    }

    public void setNumStops(int numStops) {
        this.numStops = numStops;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public void setOrdered(boolean ordered) {
        isOrdered = ordered;
    }

    //////////////////////////////////////////////////////////////////////
    //////////////////////Start of Business Logic/////////////////////////
    //////////////////////////////////////////////////////////////////////

    public static Event createEvent(Context context, String name, String date, int duration, String startingLocation, String description) {
        Event event = new Event(name, date, duration, startingLocation, description);
        long id = AppDatabase.getInstance(context).eventDao().insertEvent(event);
        event.eventId = id;
        System.out.println("Event Id is " + event.eventId);
        return event;
    }

    public static void deleteEventById(Context context, long eventId) {
        AppDatabase.getInstance(context).eventDao().deleteEventById(eventId);
        //Note: I think that because of the foreign key being set to CASCADE, deleting an Event should
        //delete all related stops. Might need to do some testing to make sure this is what is
        //actually happening. If not, will need to do some manual deletion here
    }

    public static Event getEventById(Context context, long eventId) {
        return AppDatabase.getInstance(context).eventDao().findEventById(eventId);
    }

    public static List<Event> getAllEvents(Context context) {
        return AppDatabase.getInstance(context).eventDao().getAllEvents();
    }

    public static Event updateEvent(Context context, long eventId, String name, String date, int duration, String startingLocation, String description) {
        Event event = getEventById(context.getApplicationContext(), eventId);
        event.name = name;
        event.date = date;
        event.duration = duration;
        event.startingLocation = startingLocation;
        event.description = description;
        System.out.println("Event Id is " + event.eventId);
        AppDatabase.getInstance(context).eventDao().updateEvent(event);
        return event;
    }

    public static void cloneEvent(Context context, Event event) {
        AppDatabase db = AppDatabase.getInstance(context);
        List<Stop> stops = db.stopDao().findStopsByEventId(event.eventId);
        Event clonedEvent = new Event();
        clonedEvent.name = event.name + " (clone)";
        clonedEvent.date = event.date;
        clonedEvent.duration = event.duration;
        clonedEvent.startingLocation = event.startingLocation;
        clonedEvent.description = event.description;
        long clonedEventId = db.eventDao().insertEvent(clonedEvent);
        for (Stop s : stops) {
            Stop clonedStop = new Stop(clonedEventId, s.getContentUrl(), s.getLocation());
            clonedStop.setPaired(false);
            clonedStop.setOrderNumber(s.getOrderNumber());
            db.stopDao().insertStop(clonedStop);
        }
    }

}

