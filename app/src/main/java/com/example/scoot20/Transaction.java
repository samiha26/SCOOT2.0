package com.example.scoot20;

public class Transaction {
    private String TransID;
    private String payTo;
    private double payAmount;

    public Transaction(){}
    public Transaction(String payTo, double payAmount) {
        this.payTo = payTo;
        this.payAmount = payAmount;
    }

    public String getPayTo() {
        return payTo;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public String getTransID() {
        return TransID;
    }

    public void setTransID(String transID) {
        TransID = transID;
    }

    public void setPayTo(String payTo) {
        this.payTo = payTo;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }
}
