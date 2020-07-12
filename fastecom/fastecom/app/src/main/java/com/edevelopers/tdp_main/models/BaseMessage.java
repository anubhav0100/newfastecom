package com.edevelopers.tdp_main.models;

public class BaseMessage {

    UserMessage msg;
    public UserMessage getMessage()
    {
        return msg;
    }

    public void setMessage(UserMessage message)
    {
        this.msg =message;
    }
}
