package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.RentalStatus;

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
    private RentalStatus status;
    private String customerName;
    private String eventName;
    private String processedByUsername;

    public Rental(int customerId, int eventId, int processedBy, LocalDate eventDate, LocalDate pickupDate, LocalDate dueDate) {
        this.customerId = customerId;
        this.eventId = eventId;
        this.processedBy = processedBy;
        this.eventDate = eventDate;
        this.pickupDate = pickupDate;
        this.dueDate = dueDate;
    }

    public Rental(int id, String rentalNo, int customerId, int eventId, int processedBy, LocalDate eventDate, LocalDate pickupDate, LocalDate dueDate, LocalDate returnDate, BigDecimal subtotal, BigDecimal depositAmount, BigDecimal lateFee, BigDecimal damageTotal, BigDecimal totalAmount, RentalStatus status) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getRentalNo() {
        return rentalNo;
    }

    public void setRentalNo(String rentalNo) {
        this.rentalNo = rentalNo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(int processedBy) {
        this.processedBy = processedBy;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getLateFee() {
        return lateFee;
    }

    public void setLateFee(BigDecimal lateFee) {
        this.lateFee = lateFee;
    }

    public BigDecimal getDamageTotal() {
        return damageTotal;
    }

    public void setDamageTotal(BigDecimal damageTotal) {
        this.damageTotal = damageTotal;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getProcessedByUsername() {
        return processedByUsername;
    }

    public void setProcessedByUsername(String processedByUsername) {
        this.processedByUsername = processedByUsername;
    }
}
