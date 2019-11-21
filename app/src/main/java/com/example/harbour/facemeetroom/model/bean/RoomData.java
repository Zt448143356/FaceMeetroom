package com.example.harbour.facemeetroom.model.bean;

import com.example.harbour.facemeetroom.db.entity.Room;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RoomData {
    /**
     * "status":200
     * "msg":"success"
     * "roomid":"1"
     * "data":[{"id":"1111111111","time":"8:00-10:00","content":"math","user":"nancy","date":"19-2-21"},{"id":"1111111112","time":"10:00-12:00","content":"chinese","user":"nancy","date":"19-2-21"},{"id":"1111111113","time":"12:00-14:00","content":"math","user":"nancy","date":"19-2-21"},{"id":"1111111114","time":"14:00-16:00","content":"math","user":"nancy","date":"19-2-21"},{"id":"1111111115","time":"16:00-18:00","content":"chinese","user":"nancy","date":"19-2-21"}]
     */
    @SerializedName("status")
    private int status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("roomid")
    private String roomid;
    @SerializedName("data")
    private ArrayList<Room> data;

    public void setStatus(int status){
        this.status = status;
    }

    public void setMsg(String msg){
        this.msg=msg;
    }

    public void setData(ArrayList<Room> data){
        this.data=data;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public int getStatus(){
        return status;
    }

    public String getMsg(){
        return msg;
    }

    public String getRoomid() {
        return roomid;
    }

    public ArrayList<Room> getData(){
        return data;
    }

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", roomid='" + roomid + '\'' +
                ", data=" + data +
                '}';
    }
}
