package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiGoals {
    @SerializedName("home")
    private Integer home; // Dùng Integer để có thể là null
    @SerializedName("away")
    private Integer away;
    @SerializedName("total") private Integer total;
    @SerializedName("assists") private Integer assists;

    public Integer getHome() { return home; }
    public Integer getAway() { return away; }
    public Integer getTotal() { return total; }
    public Integer getAssists() { return assists; }


}