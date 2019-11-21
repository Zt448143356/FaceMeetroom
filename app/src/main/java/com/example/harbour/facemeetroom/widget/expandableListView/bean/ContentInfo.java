package com.example.harbour.facemeetroom.widget.expandableListView.bean;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentInfo {
    @SerializedName("title")
    private String  title;
    @SerializedName("names")
    private List<String> names;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
