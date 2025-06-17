package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;

public class ApiStandingData {
    @SerializedName("league")
    private ApiLeagueWithStandings league;

    public ApiLeagueWithStandings getLeague() {
        return league;
    }
}