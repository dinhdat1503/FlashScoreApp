package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiTeamStatistics {
    @SerializedName("team")
    private ApiTeamInfo team;

    @SerializedName("statistics")
    private List<ApiStatisticItem> statistics;

    public ApiTeamInfo getTeam() {
        return team;
    }

    public List<ApiStatisticItem> getStatistics() {
        return statistics;
    }
}