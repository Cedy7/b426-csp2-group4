package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.Gender;

public class eventCategory {

    private int eventId;
    private int categoryId;
    private Gender gender;

    public eventCategory(int eventId, int categoryId, Gender gender) {
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
}
