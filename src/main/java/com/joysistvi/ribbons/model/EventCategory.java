package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.Gender;

public class EventCategory {

    private int eventId;
    private int categoryId;
    private Gender gender;
    private String eventName;
    private String categoryName;

    public EventCategory(int eventId, int categoryId, Gender gender) {
        this.eventId = eventId;
        this.categoryId = categoryId;
        this.gender = gender;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}