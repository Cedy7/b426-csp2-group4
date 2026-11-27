package com.joysistvi.ribbons.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Rental {
    private int id;
    private String rentalNo;
    private int customerId;
    private int eventId;
    private int processedBy;
    private LocalDate eventDate;
    private LocalDate pickupDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BigDecimal subtotal;
    private BigDecimal depositAmount;
    private BigDecimal lateFee;
    private BigDecimal damageTotal;
    private BigDecimal totalAmount;
    private String status;

    public Rental(String rentalNo, int customerId, int eventId, int processedBy, LocalDate eventDate, LocalDate pickupDate, LocalDate dueDate, LocalDate returnDate, BigDecimal subtotal, BigDecimal depositAmount, BigDecimal lateFee, BigDecimal damageTotal, BigDecimal totalAmount, String status) {
        this.rentalNo = rentalNo;
        this.customerId = customerId;
        this.eventId = eventId;
        this.processedBy = processedBy;
        this.eventDate = eventDate;
        this.pickupDate = pickupDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.subtotal = subtotal;
        this.depositAmount = depositAmount;
        this.lateFee = lateFee;
        this.damageTotal = damageTotal;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Rental(int id, String rentalNo, int customerId, int eventId, int processedBy, LocalDate eventDate, LocalDate pickupDate, LocalDate dueDate, LocalDate returnDate, BigDecimal subtotal, BigDecimal depositAmount, BigDecimal lateFee, BigDecimal damageTotal, BigDecimal totalAmount, String status) {
        this.id = id;
        this.rentalNo = rentalNo;
        this.customerId = customerId;
        this.eventId = eventId;
        this.processedBy = processedBy;
        this.eventDate = eventDate;
        this.pickupDate = pickupDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.subtotal = subtotal;
        this.depositAmount = depositAmount;
        this.lateFee = lateFee;
        this.damageTotal = damageTotal;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getRentalNo() {
        return rentalNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getProcessedBy() {
        return processedBy;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public BigDecimal getDamageTotal() {
        return damageTotal;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRentalNo(String rentalNo) {
        this.rentalNo = rentalNo;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setProcessedBy(int processedBy) {
        this.processedBy = processedBy;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public void setDamageTotal(BigDecimal damageTotal) {
        this.damageTotal = damageTotal;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
