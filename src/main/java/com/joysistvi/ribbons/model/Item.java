package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.Gender;
import com.joysistvi.ribbons.model.enums.ItemStatus;

import java.math.BigDecimal;

public class Item {

    private int id;
    private String sku;
    private int categoryId;
    private int sizeId;
    private String name;
    private String color;
    private Gender gender;
    private BigDecimal rentalPrice;
    private BigDecimal replacementCost;
    private ItemStatus status;
    private boolean archived;
    private String categoryName;
    private String sizeCode;

    public Item(int categoryId, int sizeId, String name, String color, Gender gender, BigDecimal rentalPrice, BigDecimal replacementCost) {
        this.categoryId = categoryId;
        this.sizeId = sizeId;
        this.name = name;
        this.color = color;
        this.gender = gender;
        this.rentalPrice = rentalPrice;
        this.replacementCost = replacementCost;
    }

    public Item(int id, String sku, int categoryId, int sizeId, String name, String color, Gender gender, BigDecimal rentalPrice, BigDecimal replacementCost, ItemStatus status, boolean archived) {
        this.id = id;
        this.sku = sku;
        this.categoryId = categoryId;
        this.sizeId = sizeId;
        this.name = name;
        this.color = color;
        this.gender = gender;
        this.rentalPrice = rentalPrice;
        this.replacementCost = replacementCost;
        this.status = status;
        this.archived = archived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BigDecimal getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(BigDecimal rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public BigDecimal getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSizeCode() {
        return sizeCode;
    }

    public void setSizeCode(String sizeCode) {
        this.sizeCode = sizeCode;
    }
}
