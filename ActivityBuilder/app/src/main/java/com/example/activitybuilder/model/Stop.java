package com.example.activitybuilder.model;

import android.content.Context;

import com.example.activitybuilder.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Event.class,
    parentColumns = "eventId",
    childColumns = "stopEventId",
    onDelete = ForeignKey.CASCADE)
})
public class Stop {

    @PrimaryKey(autoGenerate = true)
    private int stopId;
    private int stopEventId;

    /**
     * The order of the stop within the Event. Starts at 1. If the event is unordered, all stops
     * will still have an order number for simplicity in case the event is later changed to ordered
     */
    private int orderNumber;
    private String contentUrl;
    private String description;

    private boolean isPaired;

    public Stop(int stopEventId, String contentUrl) {
        this.stopEventId = stopEventId;
        this.contentUrl = contentUrl;
    }

    public Stop(int stopEventId, String contentUrl, String description) {
        this.stopEventId = stopEventId;
        this.contentUrl = contentUrl;
        this.description = description;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getStopEventId() {
        return stopEventId;
    }

    public void setStopEventId(int stopEventId) {
        this.stopEventId = stopEventId;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isPaired() {
        return isPaired;
    }

    public void setPaired(boolean paired) {
        isPaired = paired;
    }

    /**
     * Create a stop. This is the method to call from the UI
     */
    public static Stop createStop(Context context, int stopEventId, String contentUrl, String description) {
        Stop stop = new Stop(stopEventId, contentUrl, description);
        AppDatabase db = AppDatabase.getInstance(context);
        boolean isEventOrdered = db.eventDao().findEventById(stopEventId).isOrdered();
        if (isEventOrdered) {
            int numStops = db.stopDao().findStopsByEventId(stopEventId).size();
            stop.orderNumber = numStops + 1;
        } else {
            stop.orderNumber = -1;
        }
        AppDatabase.getInstance(context).stopDao().insertStop(stop);
        return stop;
    }

    /**
     * Reorder a stop by one position. Will change the orderNumber of the given Stop as well as that
     * of the neighbouring Stop it is being switched with
     *
     * @param moveEarlier Set to true to move stop 1 earlier (e.g. 3rd to 2nd) and false to move one later (e.g. 2nd to 3rd)
     */
    public static void reorderStop(Context context, Stop stop, boolean moveEarlier) {
        AppDatabase db = AppDatabase.getInstance(context);
        Stop neighbourStop;
        if (moveEarlier) {
            neighbourStop = db.stopDao().findStopByEventIdAndOrderNumber(stop.stopEventId, stop.orderNumber - 1);
            stop.orderNumber --;
            neighbourStop.orderNumber ++;
        } else {
            neighbourStop = db.stopDao().findStopByEventIdAndOrderNumber(stop.stopEventId, stop.orderNumber + 1);
            stop.orderNumber ++;
            neighbourStop.orderNumber --;
        }
        db.stopDao().updateStops(stop, neighbourStop);
    }

    public static void deleteStop(Context context, Stop stop) {
        AppDatabase db = AppDatabase.getInstance(context);
        int stopOrderNumber = stop.orderNumber;
        db.stopDao().delete(stop);

        //All stops later in the order than the deleted stop will need to have their order decreased
        //by one to make the order continuous
        ArrayList<Stop> otherStops = db.stopDao().getAllStops();
        for (Stop s : otherStops) {
            if (s.orderNumber > stopOrderNumber) {
                s.orderNumber --;
            }
        }
        //This cast should work? will need to test to be sure. If not, will need to make another dao
        //method to delete a list
        db.stopDao().updateStops((Stop[]) otherStops.toArray());
    }
}
