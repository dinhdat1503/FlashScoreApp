package com.example.flashscoreapp.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MatchDetails {
    @SerializedName("matchId")
    private int matchId;

    @SerializedName("statistics")
    private List<MatchStatistic> statistics;

    @SerializedName("events")
    private List<MatchEvent> events;

    public int getMatchId() { return matchId; }
    public MatchDetails(int matchId, List<MatchStatistic> statistics, List<MatchEvent> events) {
        this.matchId = matchId;
        this.statistics = statistics;
        this.events = events;
    }
    public List<MatchStatistic> getStatistics() { return statistics; }
    public List<MatchEvent> getEvents() { return events; }
    public void setEvents(List<MatchEvent> events) { this.events = events; }
    public void setStatistics(List<MatchStatistic> statistics) { this.statistics = statistics; }
}