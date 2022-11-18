package com.example.activitybuilder.model;

import android.content.Context;

import com.example.activitybuilder.AppDatabase;

import java.util.List;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"stopEventId"})}, foreignKeys = {@ForeignKey(entity = Event.class,
    parentColumns = {"eventId"},
    childColumns = {"stopEventId"},
    onDelete = ForeignKey.CASCADE)
})
public class Stop {

    @PrimaryKey(autoGenerate = true)
    private long stopId;
    private long stopEventId;

    /**
     * The order of the stop within the Event. Starts at 1. If the event is unordered, all stops
     * will still have an order number for simplicity in case the event is later changed to ordered
     */
    private int orderNumber;
    private String contentUrl;
    private String location;

    private boolean isPaired;

    public Stop(long stopEventId, String contentUrl, String location) {
        this.stopEventId = stopEventId;
        this.contentUrl = contentUrl;
        this.location = location;
    }

    public long getStopId() {
        return stopId;
    }

    public void setStopId(long stopId) {
        this.stopId = stopId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getStopEventId() {
        return stopEventId;
    }

    public void setStopEventId(long stopEventId) {
        this.stopEventId = stopEventId;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    public static Stop createStop(Context context, long stopEventId, String contentUrl, String location) {
        Stop stop = new Stop(stopEventId, contentUrl, location);
        AppDatabase db = AppDatabase.getInstance(context);
        System.out.println("stopEventId is " + stopEventId);
        System.out.println("Event is " + db.eventDao().findEventById(stopEventId));
        int numStops = db.stopDao().findStopsByEventId(stopEventId).size();
        stop.orderNumber = numStops + 1;
        AppDatabase.getInstance(context).stopDao().insertStop(stop);
        return stop;
    }

    public static void updateStop(Context context, Stop stop) {
        AppDatabase.getInstance(context).stopDao().updateStop(stop);
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

    public static List<Stop> getStopsByEventId(Context context, long eventId) {
        return AppDatabase.getInstance(context).stopDao().findStopsByEventId(eventId);
    }

    public static Stop updateStop(Context context, long stopId, long stopEventId, String contentUrl, String location) {
        Stop stop = AppDatabase.getInstance(context).stopDao().findStopById(stopId);
        stop.stopEventId = stopEventId;
        stop.contentUrl = contentUrl;
        stop.location = location;
        AppDatabase.getInstance(context).stopDao().updateStop(stop);
        return stop;
    }


    public static void deleteStopById(Context context, long stopId) {
        AppDatabase.getInstance(context).stopDao().deleteStopById(stopId);
        //Note: I think that because of the foreign key being set to CASCADE, deleting an Event should
        //delete all related stops. Might need to do some testing to make sure this is what is
        //actually happening. If not, will need to do some manual deletion here
    }

    public static void deleteStop(Context context, Stop stop) {
        AppDatabase db = AppDatabase.getInstance(context);
        int stopOrderNumber = stop.orderNumber;
        db.stopDao().delete(stop);

        //All stops later in the order than the deleted stop will need to have their order decreased
        //by one to make the order continuous
        List<Stop> otherStops = db.stopDao().getAllStops();
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
