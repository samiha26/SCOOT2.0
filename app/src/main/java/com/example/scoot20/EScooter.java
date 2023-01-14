package com.example.scoot20;

public class EScooter {
    private String ScooterKey;
    private String model;
    private String RegNo;

    public EScooter(){}

    public EScooter(String model, String regNo) {
        this.model = model;
        RegNo = regNo;
    }

    public String getScooterKey() {
        return ScooterKey;
    }

    public void setScooterKey(String scooterKey) {
        ScooterKey = scooterKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegNo() {
        return RegNo;
    }

    public void setRegNo(String regNo) {
        RegNo = regNo;
    }
}
