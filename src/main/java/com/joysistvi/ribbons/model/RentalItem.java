package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.ReturnCondition;

import java.math.BigDecimal;

public class RentalItem {

    private int rentalId;
    private int itemId;
    private BigDecimal unitPrice;
    private ReturnCondition returnCondition;
    private BigDecimal damageFee;
    private String returnNotes;
    private String sku;
    private String itemName;

    public RentalItem(int rentalId, int itemId, BigDecimal unitPrice) {
        this.rentalId = rentalId;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
    }

    public RentalItem(int rentalId, int itemId, BigDecimal unitPrice, ReturnCondition returnCondition, BigDecimal damageFee, String returnNotes) {
        this.rentalId = rentalId;
        this.itemId = itemId;
        this.unitPrice = unitPrice;
        this.returnCondition = returnCondition;
        this.damageFee = damageFee;
        this.returnNotes = returnNotes;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ReturnCondition getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(ReturnCondition returnCondition) {
        this.returnCondition = returnCondition;
    }

    public BigDecimal getDamageFee() {
        return damageFee;
    }

    public void setDamageFee(BigDecimal damageFee) {
        this.damageFee = damageFee;
    }

    public String getReturnNotes() {
        return returnNotes;
    }

    public void setReturnNotes(String returnNotes) {
        this.returnNotes = returnNotes;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
