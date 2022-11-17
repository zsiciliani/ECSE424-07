package com.example.activitybuilder.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stop {

    @PrimaryKey(autoGenerate = true)
    private int stopId;

    private int stopEventId;

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
}
