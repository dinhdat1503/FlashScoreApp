package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class MatchStatistic {
    @SerializedName("type")
    private String type;

    @SerializedName("home")
    private String homeValue;

    @SerializedName("away")
    private String awayValue;

    public String getType() { return type; }
    public String getHomeValue() { return homeValue; }
    public String getAwayValue() { return awayValue; }
}