package com.joysistvi.ribbons.model;

public class ItemEvent {
    private int itemId;
    private int eventId;
    private String eventName;

    public ItemEvent(int itemId, int eventId) {
        this.itemId = itemId;
        this.eventId = eventId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() { return eventName; }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
