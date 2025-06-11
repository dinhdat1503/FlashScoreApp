package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ApiStandingData {
    @SerializedName("league")
    private ApiLeagueWithStandings league;

    public ApiLeagueWithStandings getLeague() {
        return league;
    }
}