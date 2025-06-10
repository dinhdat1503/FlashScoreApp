package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiTeamInfo {
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;

    public String getName() { return name; }
    public String getLogo() { return logo; }
}