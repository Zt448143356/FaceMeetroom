package com.example.harbour.facemeetroom.model.bean;

import com.google.gson.annotations.SerializedName;

public class person {
    @SerializedName("name")
    private String name;
    @SerializedName("id")
    private String id;
    @SerializedName("picture_url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
