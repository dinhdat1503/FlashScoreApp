package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class MatchEvent {
    private int teamId;
    @SerializedName("minute")
    private int minute;

    @SerializedName("team")
    private String team; // "home" or "away"

    @SerializedName("player")
    private String player;

    @SerializedName("type")
    private String type;

    public MatchEvent(int teamId, int minute, String team, String player, String type) {
        this.teamId = teamId;
        this.minute = minute;
        this.team = team;
        this.player = player;
        this.type = type;
    }
    public int getTeamId() { return teamId; }
    public int getMinute() { return minute; }
    public String getTeam() { return team; }
    public String getPlayer() { return player; }
    public String getType() { return type; }
}