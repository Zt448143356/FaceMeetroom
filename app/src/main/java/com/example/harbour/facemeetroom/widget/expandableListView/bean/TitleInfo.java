package com.example.harbour.facemeetroom.widget.expandableListView.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TitleInfo {
    @SerializedName("status")
    private int  status;
    @SerializedName("data")
    private List<ContentInfo> contentInfos;

    public List<ContentInfo> getContentInfos() {
        return contentInfos;
    }

    public void setContentInfos(List<ContentInfo> contentInfos) {
        this.contentInfos = contentInfos;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}
