package com.example.harbour.facemeetroom.model.bean;

import com.example.harbour.facemeetroom.db.entity.User;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private User data;

    public void setData(User data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public User getData() {
        return data;
    }
}
