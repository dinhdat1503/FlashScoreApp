package com.example.flashscoreapp.ui.team_details; // Sửa lại package

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.api.RetrofitClient;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.Player;
import com.example.flashscoreapp.data.repository.MatchRepository;
import com.example.flashscoreapp.data.repository.TeamRepository;
import java.util.List;

public class TeamDetailsViewModel extends AndroidViewModel {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final LiveData<List<Match>> matchesForTeam;
    private final LiveData<List<Player>> squad;

    public TeamDetailsViewModel(@NonNull Application application, int teamId, int seasonYear) {
        super(application);
        matchRepository = new MatchRepository(application);
        teamRepository = new TeamRepository(RetrofitClient.getApiService());

        matchesForTeam = matchRepository.getMatchesForTeam(teamId, seasonYear);
        squad = teamRepository.getSquad(teamId);
    }

    public LiveData<List<Match>> getMatchesForTeam() {
        return matchesForTeam;
    }

    public LiveData<List<Player>> getSquad() {
        return squad;
    }
}