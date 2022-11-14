package com.example.activitybuilder;

public class Stop {
    private int orderNumber;
    private Event event;
    private String contentUrl;
    private String description;

    public Stop(Event event) {
        this.event = event;
    }

    public Stop(Event event, String contentUrl) {
        this.event = event;
        this.contentUrl = contentUrl;
    }

    public Stop(Event event, String contentUrl, String description) {
        this.event = event;
        this.contentUrl = contentUrl;
        this.description = description;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
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
}
