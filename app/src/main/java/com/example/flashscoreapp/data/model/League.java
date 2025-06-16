package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class League implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logoUrl;

    // SỬA LẠI HÀM KHỞI TẠO ĐỂ NHẬN ĐỦ 3 THAM SỐ
    public League(int id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
}