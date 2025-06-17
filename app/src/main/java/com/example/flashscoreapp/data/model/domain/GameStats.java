package com.example.flashscoreapp.data.model.domain;

import com.example.flashscoreapp.data.model.remote.ApiGoalsStats;
import com.google.gson.annotations.SerializedName;

public class GameStats {
    @SerializedName("played")
    private int played;

    @SerializedName("win")
    private int win;

    @SerializedName("draw")
    private int draw;

    @SerializedName("lose")
    private int lose;

    @SerializedName("goals")
    private ApiGoalsStats goals;

    public int getPlayed() { return played; }
    public int getWin() { return win; }
    public int getDraw() { return draw; }
    public int getLose() { return lose; }
    public ApiGoalsStats getGoals() { return goals; }
}