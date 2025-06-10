package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class League implements Serializable {
    @SerializedName("name")
    private String name;


    public League(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}