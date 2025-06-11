package com.example.flashscoreapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.model.League;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class LeaguesViewModel extends AndroidViewModel {
    private final MatchRepository repository;
    private final LiveData<List<League>> leagues;

    public LeaguesViewModel(@NonNull Application application) {
        super(application);
        repository = new MatchRepository(application);
        leagues = repository.getLeagues();
    }

    public LiveData<List<League>> getLeagues() {
        return leagues;
    }
}