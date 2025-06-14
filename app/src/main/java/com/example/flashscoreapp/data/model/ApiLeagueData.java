package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ApiLeagueData {
    @SerializedName("league")
    private League league;

    @SerializedName("seasons")
    private List<Season> seasons;

    public League getLeague() {
        return league;
    }
    public List<Season> getSeasons() { return seasons; }
}