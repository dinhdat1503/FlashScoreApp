package com.example.flashscoreapp.ui.team_details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

// --- SỬA TOÀN BỘ FILE NÀY ---
public class TeamDetailsViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final int teamId;

    // Bỏ seasonYear khỏi constructor
    public TeamDetailsViewModelFactory(Application application, int teamId) {
        this.application = application;
        this.teamId = teamId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TeamDetailsViewModel.class)) {
            // Truyền teamId vào ViewModel
            return (T) new TeamDetailsViewModel(application, teamId);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}