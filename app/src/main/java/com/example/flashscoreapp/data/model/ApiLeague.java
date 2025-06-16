package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ApiLeague implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("logo")
    private String logo;
    @SerializedName("round")
    private String round;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getLogo() {
        return logo;
    }
    public String getRound() {
        return round;
    }
}