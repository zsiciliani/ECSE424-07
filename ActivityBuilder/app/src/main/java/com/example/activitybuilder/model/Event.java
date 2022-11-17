package com.example.activitybuilder.model;

import java.util.ArrayList;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int eventId;

    private String name;
    private Date date;
    private int duration;
    private String startingLocation;
    private String description;
    private ArrayList<Stop> stops;
    private int numStops;

    public Event() {}

    public Event(String name, Date date, int duration, String startingLocation, String description) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    /**
     * Method to add a stop to the end of an event. Will increment the event's numStops and the
     * stop's orderNumber.
     * @param stop
     */
    public void addStop(Stop stop) {
        this.stops.add(stop);
        this.numStops++;
        stop.setOrderNumber(numStops);
    }

    public void createStop(String contentUrl, String description) {
        Stop stop = new Stop(this, contentUrl, description);
        this.numStops++;
        stop.setOrderNumber(this.numStops);
        this.stops.add(stop);
    }

    public void deleteStop(Stop stop) {
        this.stops.remove(stop);
        this.numStops --;
        for (int i = 0; i < numStops; i++) {
            this.stops.get(i).setOrderNumber(i + 1);
        }
    }
}

