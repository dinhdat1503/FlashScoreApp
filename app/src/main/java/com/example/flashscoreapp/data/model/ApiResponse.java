package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse {
    @SerializedName("response")
    private List<ApiMatch> response;

    public List<ApiMatch> getResponse() {
        return response;
    }
}