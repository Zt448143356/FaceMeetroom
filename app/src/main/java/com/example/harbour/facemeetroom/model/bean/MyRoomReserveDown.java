package com.example.harbour.facemeetroom.model.bean;

import java.util.ArrayList;

public class MyRoomReserveDown {
    private String roomid;
    private String id;
    private String date;
    private String time;
    private String content;
    private ArrayList<String> name;

    public void setContent(String content) {
        this.content = content;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public void setUsername(String username) {
        this.id = username;
    }

    public String getUsername() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
