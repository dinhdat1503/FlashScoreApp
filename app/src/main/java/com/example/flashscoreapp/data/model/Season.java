package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Season implements Serializable {
    @SerializedName("year")
    private int year;

    @SerializedName("start")
    private String start; // Dạng "YYYY-MM-DD"

    @SerializedName("end")
    private String end;   // Dạng "YYYY-MM-DD"

    public int getYear() { return year; }
    public String getStart() { return start; }
    public String getEnd() { return end; }
}