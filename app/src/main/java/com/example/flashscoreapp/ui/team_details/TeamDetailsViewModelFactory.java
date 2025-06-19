package com.example.flashscoreapp.ui.team_details;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.flashscoreapp.data.api.RetrofitClient;
import com.example.flashscoreapp.data.repository.TeamRepository;

/**
 * Factory để khởi tạo TeamDetailsViewModel, cho phép truyền tham số teamId vào constructor.
 */
public final class TeamDetailsViewModelFactory implements ViewModelProvider.Factory {
    private final int teamId;

    public TeamDetailsViewModelFactory(final int teamId) {
        this.teamId = teamId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TeamDetailsViewModel.class)) {
            // ĐÃ SỬA: Giả sử phương thức đúng là .getApiService() thay vì .getInstance().getApiService()
            final TeamRepository teamRepository = new TeamRepository(RetrofitClient.getApiService());

            @SuppressWarnings("unchecked")
            final T viewModel = (T) new TeamDetailsViewModel(teamRepository, teamId);
            return viewModel;
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}