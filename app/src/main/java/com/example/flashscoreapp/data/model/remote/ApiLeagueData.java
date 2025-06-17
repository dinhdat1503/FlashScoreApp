package com.example.flashscoreapp.data.model.remote;

import com.example.flashscoreapp.data.model.domain.League;
import com.example.flashscoreapp.data.model.domain.Season;
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