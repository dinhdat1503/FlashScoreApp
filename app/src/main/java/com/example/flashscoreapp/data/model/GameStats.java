package com.example.flashscoreapp.data.model;

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

    public int getPlayed() { return played; }
    public int getWin() { return win; }
    public int getDraw() { return draw; }
    public int getLose() { return lose; }
}