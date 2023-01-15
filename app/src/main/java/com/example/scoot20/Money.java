package com.example.scoot20;

public class Money {
    private double balance;
    private String eWalletID;

    public Money(){}

    public Money(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String geteWalletID() {
        return eWalletID;
    }

    public void seteWalletID(String eWalletID) {
        this.eWalletID = eWalletID;
    }
}
