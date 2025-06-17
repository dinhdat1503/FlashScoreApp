package com.example.flashscoreapp.data.model.domain;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Objects;

public class League implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logoUrl;
    private String country;

    public League(int id, String name, String logoUrl, String country) {
        this.id = id;
        this.name = name;
        this.logoUrl = logoUrl;
        this.country = country;
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getLogoUrl() { return logoUrl; }
    public String getCountry() { return country; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        League league = (League) o;
        return id == league.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}