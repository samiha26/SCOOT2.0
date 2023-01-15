package com.example.scoot20;

import java.io.Serializable;

public class BookingDetails implements Serializable {

    private String date;
    private String time;
    private String pAdd;
    private String pPhone;
    private String dAdd;
    private String dPhone;
    private String model;
    private String name;
    private String key;
    private String parentKey;
    private boolean hasMechanic;
    private String mechanicName;
    private boolean mechanicDone;
    private String price;
    private boolean hasPaid;
    private String mechanicID;
    private String orderID;

    public BookingDetails() {}



    public BookingDetails(String date, String time, String pAdd, String pPhone, String dAdd, String dPhone, String model) {
        this.date = date;
        this.time = time;
        this.pAdd = pAdd;
        this.pPhone = pPhone;
        this.dAdd = dAdd;
        this.dPhone = dPhone;
        this.model = model;
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

    public String getpAdd() {
        return pAdd;
    }

    public void setpAdd(String pAdd) {
        this.pAdd = pAdd;
    }

    public String getpPhone() {
        return pPhone;
    }

    public void setpPhone(String pPhone) {
        this.pPhone = pPhone;
    }

    public String getdAdd() {
        return dAdd;
    }

    public void setdAdd(String dAdd) {
        this.dAdd = dAdd;
    }

    public String getdPhone() {
        return dPhone;
    }

    public void setdPhone(String dPhone) {
        this.dPhone = dPhone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public boolean isHasMechanic() {
        return hasMechanic;
    }

    public void setHasMechanic(boolean hasMechanic) {
        this.hasMechanic = hasMechanic;
    }

    public String getMechanicName() {
        return mechanicName;
    }

    public void setMechanicName(String mechanicName) {
        this.mechanicName = mechanicName;
    }

    public boolean isMechanicDone() {
        return mechanicDone;
    }

    public void setMechanicDone(boolean mechanicDone) {
        this.mechanicDone = mechanicDone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public String getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(String mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public static Order toOrder (BookingDetails bookingDetails){
        Order order = new Order(bookingDetails.getName().toString(),
                bookingDetails.getDate().toString(),
                bookingDetails.getTime().toString(),
                bookingDetails.getModel().toString());
        return order;
    }

}
