package com.example.activitybuilder.dao;

import com.example.activitybuilder.model.EventWithStops;

import java.util.List;

import androidx.room.Query;
import androidx.room.Transaction;

public interface EventWithStopsDao {
    @Transaction
    @Query("SELECT * FROM Event")
    public List<EventWithStops> getEventsWithStops();
}
