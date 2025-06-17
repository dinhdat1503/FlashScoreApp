package com.example.flashscoreapp.data.model.remote;

import com.example.flashscoreapp.data.model.domain.StandingItem;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiLeagueWithStandings {
    @SerializedName("standings")
    private List<List<StandingItem>> standings; // API trả về một list chứa một list khác

    public List<List<StandingItem>> getStandings() {
        return standings;
    }
}