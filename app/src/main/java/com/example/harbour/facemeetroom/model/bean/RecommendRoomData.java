package com.example.harbour.facemeetroom.model.bean;

import com.example.harbour.facemeetroom.db.entity.RecommendRoom;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RecommendRoomData {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private ArrayList<RecommendRoom> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<RecommendRoom> getData() {
        return data;
    }

    public void setData(ArrayList<RecommendRoom> data) {
        this.data = data;
    }
}
