package com.example.harbour.facemeetroom.db.entity;



import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User{

    @SerializedName("id")
    @Id(autoincrement = true)
    private Long id;

    @SerializedName("name")
    @NotNull
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("age")
    private String age;

    @SerializedName("sex")
    private String sex;

    @SerializedName("picture_url")
    private String picture;

    @SerializedName("group")
    private String group;

    @Generated(hash = 2094076706)
    public User(Long id, @NotNull String name, String email, String age, String sex,
            String picture, String group) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.picture = picture;
        this.group = group;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


}
