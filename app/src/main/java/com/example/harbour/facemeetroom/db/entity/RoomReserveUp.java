package com.example.harbour.facemeetroom.db.entity;

import com.google.gson.annotations.SerializedName;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RoomReserveUp {


    @Id(autoincrement = true)
    private Long id;

    @SerializedName("roomid")
    private String roomid;

    @SerializedName("username")
    private String username;

    @SerializedName("date")
    private String date;

    @SerializedName("content")
    private String content;

    @SerializedName("time")
    private String time;

    @SerializedName("names")
    private String names;




    @Generated(hash = 1213721766)
    public RoomReserveUp(Long id, String roomid, String username, String date,
            String content, String time, String names) {
        this.id = id;
        this.roomid = roomid;
        this.username = username;
        this.date = date;
        this.content = content;
        this.time = time;
        this.names = names;
    }

    @Generated(hash = 754011771)
    public RoomReserveUp() {
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNames() {
        return this.names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomid() {
        return this.roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

}
