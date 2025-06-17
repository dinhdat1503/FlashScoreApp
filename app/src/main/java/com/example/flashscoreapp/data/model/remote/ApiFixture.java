package com.example.flashscoreapp.data.model.remote;
import com.google.gson.annotations.SerializedName;

public class ApiFixture {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private String date;
    @SerializedName("status")
    private ApiStatus status;
    @SerializedName("referee")
    private String referee;
    @SerializedName("venue")
    private ApiVenue venue;

    public int getId() { return id; }
    public String getDate() { return date; }
    public ApiStatus getStatus() { return status; }
    public String getReferee() { return referee; }
    public ApiVenue getVenue() { return venue; }
}