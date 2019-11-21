package com.example.harbour.facemeetroom.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReserveUsersdown {

    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private ArrayList<person> psersons;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<person> getPsersons() {
        return psersons;
    }

    public void setPsersons(ArrayList<person> psersons) {
        this.psersons = psersons;
    }
}
