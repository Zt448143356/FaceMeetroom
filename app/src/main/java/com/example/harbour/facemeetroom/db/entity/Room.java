package com.example.harbour.facemeetroom.db.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;


@Entity
public class Room {
    /**
     * {"id":"1111111111","time":"8:00-10:00","content":"math","user":"nancy","date":"19-2-21"}
     * {"id":"1111111112","time":"10:00-12:00","content":"chinese","user":"nancy","date":"19-2-21"}
     * {"id":"1111111113","time":"12:00-14:00","content":"math","user":"nancy","date":"19-2-21"}
     * {"id":"1111111114","time":"14:00-16:00","content":"math","user":"nancy","date":"19-2-21"}
     * {"id":"1111111115","time":"16:00-18:00","content":"chinese","user":"nancy","date":"19-2-21"}
     */

    /**
     *"id":"1111111111"
     * "time":"8:00-10:00"
     * "content":"math"
     * "user":"nancy"
     * "date":"19-2-21"
     */
    @SerializedName("id")
    @Id(autoincrement = true)
    private Long id;

    @SerializedName("time")
    @NotNull
    private String time;

    @SerializedName("content")
    private String content;

    @SerializedName("user")
    private String user;

    @SerializedName("date")
    @NotNull
    private String date;





    @Generated(hash = 1614260196)
    public Room(Long id, @NotNull String time, String content, String user, @NotNull String date) {
        this.id = id;
        this.time = time;
        this.content = content;
        this.user = user;
        this.date = date;
    }



    @Generated(hash = 703125385)
    public Room() {
    }


    
    

    @Override
    public String toString() {
        return "{"+
                "id="+id+'\''+
                ",time="+time+'\''+
                "content="+content+'\''+
                "user="+user+'\''+
                "date="+date+'\''+
                '}';
    }





    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getTime() {
        return this.time;
    }



    public void setTime(String time) {
        this.time = time;
    }



    public String getContent() {
        return this.content;
    }



    public void setContent(String content) {
        this.content = content;
    }



    public String getUser() {
        return this.user;
    }



    public void setUser(String user) {
        this.user = user;
    }



    public String getDate() {
        return this.date;
    }



    public void setDate(String date) {
        this.date = date;
    }



}
