package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Team implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("logoUrl")
    private String logoUrl;

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}