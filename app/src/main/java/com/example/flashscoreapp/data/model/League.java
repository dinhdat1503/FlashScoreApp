package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class League implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logoUrl;

    public League(String name) {
        this.name = name;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
}