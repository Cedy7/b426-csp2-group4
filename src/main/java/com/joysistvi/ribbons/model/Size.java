package com.joysistvi.ribbons.model;

public class Size {

    private int id;
    private String code;
    private String sortOrder;

    public Size(int id, String code, String sortOrder) {
        this.id = id;
        this.code = code;
        this.sortOrder = sortOrder;
    }

    public Size(String code, String sortOrder) {
        this.code = code;
        this.sortOrder = sortOrder;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
