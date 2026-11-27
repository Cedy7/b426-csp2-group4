package com.joysistvi.ribbons.model;

public class Event {
    private int id;
    private String name;
    private boolean archived;

    public Event(String name, boolean archived) {
        this.name = name;
        this.archived = archived;
    }

    public Event(int id, String name, boolean archived) {
        this.id = id;
        this.name = name;
        this.archived = archived;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
