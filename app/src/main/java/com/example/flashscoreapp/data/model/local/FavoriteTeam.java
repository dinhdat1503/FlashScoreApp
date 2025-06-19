package com.example.flashscoreapp.data.model.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_teams")
public class FavoriteTeam {
    @PrimaryKey
    public int teamId;

    public FavoriteTeam(int teamId) {
        this.teamId = teamId;
    }
}