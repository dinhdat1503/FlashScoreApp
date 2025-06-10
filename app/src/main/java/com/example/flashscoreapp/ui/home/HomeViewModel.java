package com.example.flashscoreapp.ui.home;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData; // THAY ĐỔI QUAN TRỌNG: Import lớp MediatorLiveData
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MatchRepository matchRepository;
    private final MediatorLiveData<List<Match>> matches = new MediatorLiveData<>();
    private LiveData<List<Match>> currentDataSource;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.matchRepository = new MatchRepository();
    }

    public void fetchMatchesForDate(String date) {
        LiveData<List<Match>> newDataSource = matchRepository.getMatchesByDateFromApi(date);

        if (currentDataSource != null) {
            matches.removeSource(currentDataSource);
        }

        currentDataSource = newDataSource;
        matches.addSource(currentDataSource, matchList -> {
            matches.setValue(matchList);
        });
    }

    // Trả về LiveData, không thay đổi
    public LiveData<List<Match>> getMatches() {
        return matches;
    }
}