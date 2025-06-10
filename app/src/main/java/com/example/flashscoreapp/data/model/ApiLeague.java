package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiLeague {
    @SerializedName("name")
    private String name;

    public String getName() { return name; }
}