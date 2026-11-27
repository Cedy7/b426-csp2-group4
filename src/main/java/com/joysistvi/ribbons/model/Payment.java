package com.joysistvi.ribbons.model;

import com.joysistvi.ribbons.model.enums.PaymentMethod;
import com.joysistvi.ribbons.model.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private int id;
    private int rentalId;
    private int receivedBy;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentMethod paymentMethod;
    private String referenceNo;
    private LocalDateTime paidAt;
    private String receivedByUsername;

    public Payment(int rentalId, int receivedBy, BigDecimal amount, PaymentType paymentType, PaymentMethod paymentMethod, String referenceNo) {
        this.rentalId = rentalId;
        this.receivedBy = receivedBy;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.referenceNo = referenceNo;
    }

    public Payment(int id, int rentalId, int receivedBy, BigDecimal amount, PaymentType paymentType, PaymentMethod paymentMethod, String referenceNo, LocalDateTime paidAt) {
        this.id = id;
        this.rentalId = rentalId;
        this.receivedBy = receivedBy;
        this.amount = amount;
        this.paymentType = paymentType;
        this.paymentMethod = paymentMethod;
        this.referenceNo = referenceNo;
        this.paidAt = paidAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public int getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(int receivedBy) {
        this.receivedBy = receivedBy;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public String getReceivedByUsername() {
        return receivedByUsername;
    }

    public void setReceivedByUsername(String receivedByUsername) {
        this.receivedByUsername = receivedByUsername;
    }
}
