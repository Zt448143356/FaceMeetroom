package com.example.harbour.facemeetroom.model.bean;

public class SearchPost {
    private String roomId;
    private String roomTime;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setRoomTime(String roomTime) {
        this.roomTime = roomTime;
    }

    public String getRoomTime() {
        return roomTime;
    }

    public String getRoomId() {
        return roomId;
    }
}
