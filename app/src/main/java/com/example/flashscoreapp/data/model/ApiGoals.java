package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiGoals {
    @SerializedName("home")
    private Integer home; // Dùng Integer để có thể là null
    @SerializedName("away")
    private Integer away;

    public Integer getHome() { return home; }
    public Integer getAway() { return away; }
}