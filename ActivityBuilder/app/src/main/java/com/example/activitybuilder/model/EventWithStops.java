package com.example.activitybuilder.model;

//Trying to follow the example for one-to-many relationship from here https://developer.android.com/training/data-storage/room/relationships

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class EventWithStops {
    @Embedded
    public Event event;

    @Relation(
            parentColumn = "eventId",
            entityColumn = "stopEventId"
    )
    public List<Stop> stops;
}
