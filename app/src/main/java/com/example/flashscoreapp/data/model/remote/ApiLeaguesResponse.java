package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiLeaguesResponse {
    @SerializedName("response")
    private List<ApiLeagueData> response;

    public List<ApiLeagueData> getResponse() {
        return response;
    }
}