package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiStatisticsResponse {
    @SerializedName("response")
    private List<ApiTeamStatistics> response;

    public List<ApiTeamStatistics> getResponse() {
        return response;
    }
}