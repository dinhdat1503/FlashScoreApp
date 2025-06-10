package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Match implements Serializable {
    @SerializedName("matchId")
    private int matchId;

    @SerializedName("league")
    private League league;

    @SerializedName("homeTeam")
    private Team homeTeam;

    @SerializedName("awayTeam")
    private Team awayTeam;

    @SerializedName("matchTime")
    private long matchTime;

    @SerializedName("status")
    private String status;

    @SerializedName("score")
    private Score score;

    // Getters for all fields
    public int getMatchId() { return matchId; }
    public League getLeague() { return league; }
    public Team getHomeTeam() { return homeTeam; }
    public Team getAwayTeam() { return awayTeam; }
    public long getMatchTime() { return matchTime; }
    public String getStatus() { return status; }
    public Score getScore() { return score; }
}