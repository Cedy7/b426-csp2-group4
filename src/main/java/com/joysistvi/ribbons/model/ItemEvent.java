package com.joysistvi.ribbons.model;

public class ItemEvent {
    private int itemId;
    private int eventId;

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

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
