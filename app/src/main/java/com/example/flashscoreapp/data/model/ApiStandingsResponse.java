package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiStandingsResponse {
    @SerializedName("response")
    private List<ApiStandingData> response;

    public List<ApiStandingData> getResponse() {
        return response;
    }
}