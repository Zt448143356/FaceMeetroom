package com.example.harbour.facemeetroom.model.bean;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("person")
    private person person;

    public com.example.harbour.facemeetroom.model.bean.person getPerson() {
        return person;
    }

    public void setPerson(com.example.harbour.facemeetroom.model.bean.person person) {
        this.person = person;
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

}
