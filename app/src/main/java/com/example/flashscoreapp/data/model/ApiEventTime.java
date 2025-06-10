package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiEventTime {
    @SerializedName("elapsed")
    private int elapsed;

    public int getElapsed() { return elapsed; }
}