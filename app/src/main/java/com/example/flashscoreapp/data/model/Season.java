package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Season implements Serializable {
    // THÊM TRƯỜNG ID VÀO ĐÂY
    @SerializedName("id")
    private int id;

    @SerializedName("year")
    private int year;

    @SerializedName("start")
    private String start;

    @SerializedName("end")
    private String end;

    // THÊM GETTER CHO ID VÀO ĐÂY
    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}