package com.example.flashscoreapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StandingsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int leagueId;
    // Xóa season khỏi đây

    public StandingsViewModelFactory(Application application, int leagueId) {
        this.application = application;
        this.leagueId = leagueId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StandingsViewModel.class)) {
            // Chỉ truyền application và leagueId
            return (T) new StandingsViewModel(application, leagueId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}