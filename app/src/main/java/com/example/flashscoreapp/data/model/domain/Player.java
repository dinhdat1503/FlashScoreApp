// app/src/main/java/com/example/flashscoreapp/data/model/domain/Player.java
package com.example.flashscoreapp.data.model.domain;

import androidx.annotation.NonNull;

public final class Player implements SquadListItem { // Thêm implements SquadListItem
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

    @Override // Thêm phương thức getItemType()
    public int getItemType() {
        return TYPE_PLAYER;
    }

    // Cần ghi đè equals và hashCode để DiffUtil hoạt động đúng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id &&
                age == player.age &&
                number == player.number &&
                name.equals(player.name) &&
                position.equals(player.position) &&
                (photoUrl != null ? photoUrl.equals(player.photoUrl) : player.photoUrl == null);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        result = 31 * result + number;
        result = 31 * result + position.hashCode();
        result = 31 * result + (photoUrl != null ? photoUrl.hashCode() : 0);
        return result;
    }
}