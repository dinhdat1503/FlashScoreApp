package com.example.flashscoreapp.ui.leaguedetails.standings;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StandingsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int leagueId;
    private final int season;

    public StandingsViewModelFactory(Application application, int leagueId, int season) {
        this.application = application;
        this.leagueId = leagueId;
        this.season = season;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StandingsViewModel.class)) {
            return (T) new StandingsViewModel(application, leagueId, season);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}