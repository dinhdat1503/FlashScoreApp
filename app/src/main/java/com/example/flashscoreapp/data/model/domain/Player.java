package com.example.flashscoreapp.data.model.domain;
public final class Player {
    private final int id;
    private final String name;
    private final int age;
    private final int number;
    private final String position;
    private final String photoUrl;

    public Player(final int id, final String name, final int age, final int number, final String position, final String photoUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.number = number;
        this.position = position;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getNumber() {
        return number;
    }

    public String getPosition() {
        return position;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}