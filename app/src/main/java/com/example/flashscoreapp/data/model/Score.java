package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Score implements Serializable {
    @SerializedName("home")
    private int home;

    @SerializedName("away")
    private int away;

    // --- CONSTRUCTOR MỚI ---
    public Score(int home, int away) {
        this.home = home;
        this.away = away;
    }

    public int getHome() {
        return home;
    }

    public int getAway() {
        return away;
    }
}