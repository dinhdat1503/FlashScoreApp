package com.example.flashscoreapp.data.model.remote;
import com.google.gson.annotations.SerializedName;
public class ApiPlayer {
    @SerializedName("name") private String name;
    @SerializedName("photo") private String photo;
    public String getName() { return name; }
    public String getPhoto() { return photo; }
}