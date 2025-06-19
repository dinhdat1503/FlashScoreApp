package com.example.flashscoreapp.ui.teamdetails;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class TeamDetailsViewModel extends AndroidViewModel {
    private final MatchRepository repository;
    private final LiveData<List<Match>> matchesForTeam;

    public TeamDetailsViewModel(@NonNull Application application, int teamId, int seasonYear) {
        super(application);
        repository = new MatchRepository(application);
        matchesForTeam = repository.getMatchesForTeam(teamId, seasonYear);
        // Sẽ thêm các LiveData khác cho đội hình, bxh... ở đây
    }

    public LiveData<List<Match>> getMatchesForTeam() {
        return matchesForTeam;
    }
}