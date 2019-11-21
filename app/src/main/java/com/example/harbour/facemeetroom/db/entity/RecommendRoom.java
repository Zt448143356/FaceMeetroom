package com.example.harbour.facemeetroom.db.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RecommendRoom {

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("number")
    @NotNull
    private String roomid;

    @SerializedName("content")
    @NotNull
    private String content;

    @Generated(hash = 759773841)
    public RecommendRoom(Long id, @NotNull String roomid, @NotNull String content) {
        this.id = id;
        this.roomid = roomid;
        this.content = content;
    }

    @Generated(hash = 1756833763)
    public RecommendRoom() {
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
