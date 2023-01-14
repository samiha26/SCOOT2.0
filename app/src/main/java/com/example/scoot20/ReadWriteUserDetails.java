package com.example.scoot20;

public class ReadWriteUserDetails {
    public String firstName, lastName, mobileNo, fullName, emailAddress;

    public ReadWriteUserDetails() {
    }

    public ReadWriteUserDetails(String textFirstName, String textLastName, String textFullName, String textEmailAddress, String textMobileNo){
        this.firstName = textFirstName;
        this.lastName = textLastName;
        this.fullName = textFullName;
        this.emailAddress = textEmailAddress;
        this.mobileNo = textMobileNo;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
