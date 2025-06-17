package com.example.flashscoreapp.ui.leaguedetails.standings;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.model.domain.StandingItem;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class StandingsViewModel extends AndroidViewModel {
    private final LiveData<List<StandingItem>> standings;

    public StandingsViewModel(@NonNull Application application, int leagueId, int season) {
        super(application);
        MatchRepository repository = new MatchRepository(application);
        standings = repository.getStandings(leagueId, season);
    }

    public LiveData<List<StandingItem>> getStandings() {
        return standings;
    }
}