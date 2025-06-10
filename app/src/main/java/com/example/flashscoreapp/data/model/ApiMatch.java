package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiMatch {
    @SerializedName("fixture")
    private ApiFixture fixture;

    @SerializedName("league")
    private ApiLeague league;

    @SerializedName("teams")
    private ApiTeams teams;

    @SerializedName("goals")
    private ApiGoals goals;

    @SerializedName("events")
    private List<ApiEvent> events;

    public ApiFixture getFixture() { return fixture; }
    public ApiLeague getLeague() { return league; }
    public ApiTeams getTeams() { return teams; }
    public ApiGoals getGoals() { return goals; }
    public List<ApiEvent> getEvents() { return events; }
}