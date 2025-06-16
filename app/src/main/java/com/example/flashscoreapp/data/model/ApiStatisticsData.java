package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;
public class ApiStatisticsData {
    @SerializedName("team") private ApiTeamInfo team;
    @SerializedName("goals") private ApiGoals goals;
    public ApiTeamInfo getTeam() { return team; }
    public ApiGoals getGoals() { return goals; }
}