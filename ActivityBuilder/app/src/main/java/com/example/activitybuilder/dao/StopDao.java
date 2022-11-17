package com.example.activitybuilder.dao;

import com.example.activitybuilder.model.Stop;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StopDao {

    @Query("SELECT * FROM Stop")
    List<Stop> getAllStops();

    @Query("SELECT * FROM Stop WHERE stopId = :id")
    Stop findStopById(int id);

    @Insert
    public void insertStop(Stop stop);

    @Insert
    public void insertStops(Stop... stops);

    @Update
    public void updateStop(Stop stop);

    @Update
    public void updateStops(Stop... stops);

    @Delete
    void delete(Stop stop);

}
