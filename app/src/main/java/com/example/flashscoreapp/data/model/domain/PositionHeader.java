package com.example.flashscoreapp.data.model.domain;

import androidx.annotation.NonNull;

public final class PositionHeader implements SquadListItem {
    private final String positionName; // Tên vị trí (vd: Thủ môn, Hậu vệ)

    public PositionHeader(@NonNull final String positionName) {
        this.positionName = positionName;
    }

    @NonNull
    public String getPositionName() {
        return positionName;
    }

    @Override
    public int getItemType() {
        return TYPE_HEADER;
    }

    // Cần ghi đè equals và hashCode để DiffUtil hoạt động đúng
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionHeader that = (PositionHeader) o;
        return positionName.equals(that.positionName);
    }

    @Override
    public int hashCode() {
        return positionName.hashCode();
    }
}