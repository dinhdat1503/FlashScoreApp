package com.example.flashscoreapp.data.model.remote;
import com.google.gson.annotations.SerializedName;

public class ApiEventPlayer {
    @SerializedName("name")
    private String name;

    public String getName() { return name; }
}