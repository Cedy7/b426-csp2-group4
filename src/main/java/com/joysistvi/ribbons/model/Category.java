package com.joysistvi.ribbons.model;

public class Category {

    private int id;
    private String code;
    private String name;
    private String description;
    private boolean isArchived;

    public Category(String code, String name, String description, boolean isArchived) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.isArchived = isArchived;
    }

    public Category(int id, String code, String name, String description, boolean isArchived) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.isArchived = isArchived;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }


}
