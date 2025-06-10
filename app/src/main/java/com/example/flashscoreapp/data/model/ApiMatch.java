package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ApiMatch {
    @SerializedName("fixture")
    private ApiFixture fixture;

    @SerializedName("league")
    private ApiLeague league;

    @SerializedName("teams")
    private ApiTeams teams;

    @SerializedName("goals")
    private ApiGoals goals;

    public ApiFixture getFixture() { return fixture; }
    public ApiLeague getLeague() { return league; }
    public ApiTeams getTeams() { return teams; }
    public ApiGoals getGoals() { return goals; }
}