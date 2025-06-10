package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class MatchEvent {
    @SerializedName("minute")
    private int minute;

    @SerializedName("team")
    private String team; // "home" or "away"

    @SerializedName("player")
    private String player;

    @SerializedName("type")
    private String type; // "Bàn thắng", "Thẻ vàng", etc.

    public int getMinute() { return minute; }
    public String getTeam() { return team; }
    public String getPlayer() { return player; }
    public String getType() { return type; }
}