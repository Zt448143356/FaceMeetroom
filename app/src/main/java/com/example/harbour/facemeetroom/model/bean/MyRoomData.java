package com.example.harbour.facemeetroom.model.bean;



import com.example.harbour.facemeetroom.db.entity.RoomReserveUp;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyRoomData {
    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private ArrayList<RoomReserveUp> data;

    public void setStatus(int status){
        this.status = status;
    }

    public void setMsg(String msg){
        this.msg=msg;
    }

    public void setData(ArrayList<RoomReserveUp> data){
        this.data=data;
    }


    public int getStatus(){
        return status;
    }

    public String getMsg(){
        return msg;
    }


    public ArrayList<RoomReserveUp> getData(){
        return data;
    }
}
