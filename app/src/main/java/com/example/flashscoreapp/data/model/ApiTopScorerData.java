package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class ApiTopScorerData {
    @SerializedName("player") private ApiPlayer player;
    @SerializedName("statistics") private List<ApiStatisticsData> statistics;
    public ApiPlayer getPlayer() { return player; }
    public List<ApiStatisticsData> getStatistics() { return statistics; }
}