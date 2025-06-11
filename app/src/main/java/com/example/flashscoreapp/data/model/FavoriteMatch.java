package com.example.flashscoreapp.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.flashscoreapp.data.db.Converters;

@Entity(tableName = "favorite_matches")
public class FavoriteMatch {

    @PrimaryKey
    private int matchId;
    private long matchTime;
    @TypeConverters(Converters.class)
    @ColumnInfo(name = "match_object")
    private Match match;

    // Sửa lại constructor
    public FavoriteMatch(int matchId, long matchTime, Match match) {
        this.matchId = matchId;
        this.matchTime = matchTime;
        this.match = match;
    }

    // Getter và Setter
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public long getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(long matchTime) {
        this.matchTime = matchTime;
    }
}