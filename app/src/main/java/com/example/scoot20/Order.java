package com.example.scoot20;

import com.google.firebase.database.Exclude;

public class Order{
    @Exclude
    private String key;
    private String name;
    private String date;
    private String time;
    private String model;
    private String userID;
    private boolean status;

    public Order(){}

    public Order(String name, String date, String time, String model) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}
