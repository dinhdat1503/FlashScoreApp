package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiStatus {
    @SerializedName("long")
    private String longStatus;
    @SerializedName("short")
    private String shortStatus;

    public String getLongStatus() { return longStatus; }
    public String getShortStatus() { return shortStatus; }
}