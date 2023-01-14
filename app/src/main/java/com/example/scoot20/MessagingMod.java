package com.example.scoot20;

public class MessagingMod {
    private String msgID;
    private String fullName;
    private String message;

    public MessagingMod(String msgID, String fullName, String message) {
        this.msgID = msgID;
        this.fullName = fullName;
        this.message = message;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
