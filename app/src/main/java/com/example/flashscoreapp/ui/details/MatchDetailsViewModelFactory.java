package com.example.flashscoreapp.ui.details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MatchDetailsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int matchId;

    public MatchDetailsViewModelFactory(Application application, int matchId) {
        this.application = application;
        this.matchId = matchId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MatchDetailsViewModel.class)) {
            return (T) new MatchDetailsViewModel(application, matchId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}