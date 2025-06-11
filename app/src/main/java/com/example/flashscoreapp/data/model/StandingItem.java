package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class StandingItem {
    @SerializedName("rank")
    private int rank;

    @SerializedName("team")
    private ApiTeamInfo team;

    @SerializedName("points")
    private int points;

    @SerializedName("goalsDiff")
    private int goalsDiff;

    @SerializedName("all") // "all" chứa thông số tổng quát
    private GameStats all;

    public int getRank() { return rank; }
    public ApiTeamInfo getTeam() { return team; }
    public int getPoints() { return points; }
    public int getGoalsDiff() { return goalsDiff; }
    public GameStats getAll() { return all; }
}