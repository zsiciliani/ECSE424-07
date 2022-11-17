package com.example.activitybuilder.dao;

import com.example.activitybuilder.model.Event;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface EventDao {

    @Query("SELECT * FROM Event")
    public List<Event> getAllEvents();

    @Query("SELECT * FROM Event WHERE eventId = :id")
    public Event findEventById(int id);

    @Insert
    public void insertEvent(Event event);

    @Insert
    public void insertEvents(Event... events);

    @Update
    public void updateEvent(Event event);

    @Update
    public void updateEvents(Event... events);

    @Delete
    void delete(Event event);
}
