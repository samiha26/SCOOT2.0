package com.example.scoot20;

public class BookingDetails {

    private String date;
    private String time;
    private String pAdd;
    private String pPhone;
    private String dAdd;
    private String dPhone;
    private String model;
    private String name;
    private String key;

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

    public static Order toOrder (BookingDetails bookingDetails){
        Order order = new Order(bookingDetails.getName().toString(),
                bookingDetails.getDate().toString(),
                bookingDetails.getTime().toString(),
                bookingDetails.getModel().toString());
        return order;
    }

}
