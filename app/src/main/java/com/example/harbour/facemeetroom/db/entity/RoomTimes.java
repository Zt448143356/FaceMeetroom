package com.example.harbour.facemeetroom.db.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RoomTimes {

    @Id(autoincrement = true)
    private Long id;

    @SerializedName("now_order_number")
    @NotNull
    private int nowNumber;

    @SerializedName("day_order_number")
    @NotNull
    private int dayNumber;

    @Generated(hash = 1043062869)
    public RoomTimes(Long id, int nowNumber, int dayNumber) {
        this.id = id;
        this.nowNumber = nowNumber;
        this.dayNumber = dayNumber;
    }

    @Generated(hash = 668065023)
    public RoomTimes() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNowNumber() {
        return this.nowNumber;
    }

    public void setNowNumber(int nowNumber) {
        this.nowNumber = nowNumber;
    }

    public int getDayNumber() {
        return this.dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }


}
