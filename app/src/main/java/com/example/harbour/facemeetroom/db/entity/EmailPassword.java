package com.example.harbour.facemeetroom.db.entity;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class EmailPassword {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private String picture;


    @Generated(hash = 1266725190)
    public EmailPassword(Long id, @NotNull String email, @NotNull String password,
            String picture) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    @Generated(hash = 534844768)
    public EmailPassword() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
