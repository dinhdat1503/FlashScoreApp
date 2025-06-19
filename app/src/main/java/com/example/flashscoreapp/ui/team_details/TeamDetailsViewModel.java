package com.example.flashscoreapp.ui.team_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.flashscoreapp.data.model.domain.Player;
import com.example.flashscoreapp.data.repository.TeamRepository;

import java.util.List;

/**
 * ViewModel chịu trách nhiệm cung cấp dữ liệu cho TeamDetailsActivity và các Fragment con.
 */
public final class TeamDetailsViewModel extends ViewModel {
    private final TeamRepository teamRepository;
    private final int teamId;

    private final LiveData<List<Player>> squad;

    public TeamDetailsViewModel(final TeamRepository teamRepository, final int teamId) {
        this.teamRepository = teamRepository;
        this.teamId = teamId;
        this.squad = teamRepository.getSquad(teamId);
    }

    /**
     * Cung cấp LiveData chứa danh sách đội hình.
     * UI sẽ quan sát (observe) LiveData này để nhận cập nhật.
     * @return LiveData chứa danh sách cầu thủ.
     */
    public LiveData<List<Player>> getSquad() {
        return squad;
    }
}