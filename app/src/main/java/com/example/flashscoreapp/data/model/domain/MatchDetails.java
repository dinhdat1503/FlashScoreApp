package com.example.flashscoreapp.data.model.domain;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MatchDetails {
    @SerializedName("matchId")
    private int matchId;

    @SerializedName("statistics")
    private List<MatchStatistic> statistics;

    @SerializedName("events")
    private List<MatchEvent> events;

    private String referee;
    private String stadium;

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
    public String getReferee() { return referee; }
    public void setReferee(String referee) { this.referee = referee; }
    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }
}