package com.edevelopers.fastecom.models;

/**
 * Created by Anubhav Singh on 3/18/2020
 */

public class UserMessage {
    private String message;
    private Sender sender;
    private String createdAt;


    // private int teamWins;
    public UserMessage(String message, Sender sender, String createdAt ) {
        super();
        this.message = message;
        this.sender = sender;
        this.createdAt = createdAt;
    }

    public UserMessage() {
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message =message;
    }
    public String getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedAt(String createdAt)
    {
        this.createdAt =createdAt;
    }
    public Sender getSender()
    {
        return sender;
    }
    public void setSender(Sender sender)
    {
        this.sender =sender;
    }

    public void set(Object ref, String trim) {


    }
}

