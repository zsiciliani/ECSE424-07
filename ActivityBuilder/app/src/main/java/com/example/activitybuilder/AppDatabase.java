package com.example.activitybuilder;

import android.content.Context;

import com.example.activitybuilder.dao.EventDao;
import com.example.activitybuilder.dao.StopDao;
import com.example.activitybuilder.model.Event;
import com.example.activitybuilder.model.Stop;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class, Stop.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract StopDao stopDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "App_Database").allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
