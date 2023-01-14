package com.example.scoot20;

public class Transaction {
    private String payTo;
    private double payAmount;

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
}
