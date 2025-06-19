package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;

public final class ApiPlayerInSquad {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("age")
    private int age;

    @SerializedName("number")
    private int number;

    @SerializedName("position")
    private String position;

    @SerializedName("photo")
    private String photo;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public int getNumber() { return number; }
    public String getPosition() { return position; }
    public String getPhoto() { return photo; }
}
