package com.example.flashscoreapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.model.League;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;
import com.example.flashscoreapp.data.model.ApiLeagueData;

public class LeaguesViewModel extends AndroidViewModel {
    private final MatchRepository repository;
    private final LiveData<List<ApiLeagueData>> leaguesData;

    public LeaguesViewModel(@NonNull Application application) {
        super(application);
        repository = new MatchRepository(application);
        // THAY ĐỔI LỜI GỌI HÀM Ở ĐÂY
        leaguesData = repository.getLeaguesWithSeasons();
    }

    public LiveData<List<ApiLeagueData>> getLeaguesData() {
        return leaguesData;
    }
}