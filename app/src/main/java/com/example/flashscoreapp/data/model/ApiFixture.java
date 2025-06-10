package com.example.flashscoreapp.data.model;
import com.google.gson.annotations.SerializedName;

public class ApiFixture {
    @SerializedName("id")
    private int id;
    @SerializedName("date")
    private String date; // API trả về dạng ISO 8601 String
    @SerializedName("status")
    private ApiStatus status;

    public int getId() { return id; }
    public String getDate() { return date; }
    public ApiStatus getStatus() { return status; }
}