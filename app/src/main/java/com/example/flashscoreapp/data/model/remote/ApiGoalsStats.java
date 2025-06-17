package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ApiGoalsStats implements Serializable {
    @SerializedName("for")
    private int goalsFor;

    @SerializedName("against")
    private int goalsAgainst;

    public int getGoalsFor() { return goalsFor; }
    public int getGoalsAgainst() { return goalsAgainst; }
}