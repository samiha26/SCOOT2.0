package com.example.scoot20;

public class BookingDetails {

    private String date;
    private String time;
    private String pAdd;
    private String pPhone;
    private String dAdd;
    private String dPhone;

    public BookingDetails(String date, String time, String pAdd, int length, String dAdd, int i) {}

    public BookingDetails(String date, String time, String pAdd, String pPhone, String dAdd, String dPhone) {
        this.date = date;
        this.time = time;
        this.pAdd = pAdd;
        this.pPhone = pPhone;
        this.dAdd = dAdd;
        this.dPhone = dPhone;
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
}
