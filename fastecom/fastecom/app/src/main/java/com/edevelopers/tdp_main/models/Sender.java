package com.edevelopers.tdp_main.models;

/**
 * Created by Anubhav Singh on 3/18/2020
 */


public class Sender {
    private String nickname,profileUrl,UserId;

    // private int teamWins;
    public Sender(String UserId, String nickname, String profileUrl ) {
        super();
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.UserId=UserId;

    }

    public Sender() {
    }

    public String getUserId()
    {
        return UserId;
    }

    public void setUserId(String UserId)
    {
        this.UserId =UserId;
    }
    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname =nickname;
    }
    public String getProfileUrl()
    {
        return profileUrl;
    }
    public void setProfileUrl(String profileUrl)
    {
        this.profileUrl =profileUrl;
    }
    public void set(Object ref, String trim) {


    }
}

