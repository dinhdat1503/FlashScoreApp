package com.example.flashscoreapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TopScorersViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int leagueId;
    private final int seasonYear;

    public TopScorersViewModelFactory(Application application, int leagueId, int seasonYear) {
        this.application = application;
        this.leagueId = leagueId;
        this.seasonYear = seasonYear;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TopScorersViewModel.class)) {
            return (T) new TopScorersViewModel(application, leagueId, seasonYear);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}