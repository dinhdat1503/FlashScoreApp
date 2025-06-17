package com.example.flashscoreapp.data.model.domain;

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
    public void setAwayValue(String awayValue) { this.awayValue = awayValue; }

    public MatchStatistic(String type, String homeValue, String awayValue) {
        this.type = type;
        this.homeValue = homeValue;
        this.awayValue = awayValue;
    }
}