package com.example.flashscoreapp.data.model.remote;
import com.google.gson.annotations.SerializedName;

public class ApiEvent {
    @SerializedName("time")
    private ApiEventTime time;
    @SerializedName("team")
    private ApiTeamInfo team;
    @SerializedName("player")
    private ApiEventPlayer player;
    @SerializedName("type")
    private String type;
    @SerializedName("detail")
    private String detail;

    public ApiEventTime getTime() { return time; }
    public ApiTeamInfo getTeam() { return team; }
    public ApiEventPlayer getPlayer() { return player; }
    public String getType() { return type; }
    public String getDetail() { return detail; }
}