package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.ReturnCondition;

public class RentalItem {

    private int rentalId;
    private int itemId;
    private double unitPrice;
    private ReturnCondition returnCondition;
    private double damageFee;
    private String returnNotes;

    public RentalItem(int rentalId, int itemId, double unitPrice, ReturnCondition returnCondition, double damageFee, String returnNotes) {
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ReturnCondition getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(ReturnCondition returnCondition) {
        this.returnCondition = returnCondition;
    }

    public double getDamageFee() {
        return damageFee;
    }

    public void setDamageFee(double damageFee) {
        this.damageFee = damageFee;
    }

    public String getReturnNotes() {
        return returnNotes;
    }

    public void setReturnNotes(String returnNotes) {
        this.returnNotes = returnNotes;
    }
}
