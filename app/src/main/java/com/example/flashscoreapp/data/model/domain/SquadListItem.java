package com.example.flashscoreapp.data.model.domain;

// Interface này giúp SquadAdapter phân biệt giữa header và item cầu thủ
public interface SquadListItem {
    int TYPE_HEADER = 0;
    int TYPE_PLAYER = 1;

    int getItemType();
}