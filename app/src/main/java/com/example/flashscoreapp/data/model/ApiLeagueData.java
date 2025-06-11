package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

public class ApiLeagueData {
    @SerializedName("league")
    private League league;

    public League getLeague() {
        return league;
    }
}