package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;

public class ApiStatisticItem {
    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private Object value; // Dùng Object vì giá trị có thể là số hoặc chuỗi (vd: "55%")

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}