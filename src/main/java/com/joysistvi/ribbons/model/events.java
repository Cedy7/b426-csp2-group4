package com.joysistvi.ribbons.model;

public class events {
    private int id;
    private String name;
    private boolean events;

    public events(String name, boolean events) {
        this.name = name;
        this.events = events;
    }

    public events(int id, String name, boolean events) {
        this.id = id;
        this.name = name;
        this.events = events;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEvents() {
        return events;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvents(boolean events) {
        this.events = events;
    }
}
