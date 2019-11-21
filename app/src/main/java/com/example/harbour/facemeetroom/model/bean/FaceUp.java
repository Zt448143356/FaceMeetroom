package com.example.harbour.facemeetroom.model.bean;

public class FaceUp {
    private String id;
    private byte[] data;

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}
