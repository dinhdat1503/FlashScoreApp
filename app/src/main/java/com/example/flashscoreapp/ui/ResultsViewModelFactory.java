package com.example.flashscoreapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class ResultsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int leagueId;
    private final int season;

    public ResultsViewModelFactory(Application application, int leagueId, int season) {
        this.application = application;
        this.leagueId = leagueId;
        this.season = season;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ResultsViewModel.class)) {
            return (T) new ResultsViewModel(application, leagueId, season);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}