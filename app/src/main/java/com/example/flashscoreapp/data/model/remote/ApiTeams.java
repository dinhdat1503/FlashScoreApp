package com.example.flashscoreapp.data.model.remote;
import com.google.gson.annotations.SerializedName;

public class ApiTeams {
    @SerializedName("home")
    private ApiTeamInfo home;
    @SerializedName("away")
    private ApiTeamInfo away;

    public ApiTeamInfo getHome() { return home; }
    public ApiTeamInfo getAway() { return away; }
}