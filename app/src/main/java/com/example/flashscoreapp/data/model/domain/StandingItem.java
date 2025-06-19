package com.example.flashscoreapp.data.model.domain;

import com.example.flashscoreapp.data.model.remote.ApiTeamInfo;
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

    @SerializedName("group")
    private String group;
    @SerializedName("all")
    private GameStats all;

    public int getRank() { return rank; }
    public ApiTeamInfo getTeam() { return team; }
    public int getPoints() { return points; }
    public int getGoalsDiff() { return goalsDiff; }
    public String getGroup() { return group; }
    public GameStats getAll() { return all; }
}