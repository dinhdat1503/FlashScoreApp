package com.example.flashscoreapp.data.model.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Team implements Serializable {
    private int id;
    @SerializedName("name")
    private String name;

    @SerializedName("logoUrl")
    private String logoUrl;

    public Team(int id, String name, String logoUrl) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public int getId() { return id; }
    public String getName() {
        return name;
    }
    public String getLogoUrl() {
        return logoUrl;
    }
}