package com.example.flashscoreapp.data.model.remote;
import com.google.gson.annotations.SerializedName;

public class ApiTeamInfo {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;

    public int getId() { return id; }

    public String getName() { return name; }
    public String getLogo() { return logo; }
}