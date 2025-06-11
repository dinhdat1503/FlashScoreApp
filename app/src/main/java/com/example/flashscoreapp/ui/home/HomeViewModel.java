package com.example.flashscoreapp.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.repository.MatchRepository;
import com.example.flashscoreapp.data.model.FavoriteMatch;
import com.example.flashscoreapp.data.model.Match;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MatchRepository matchRepository;
    private final MediatorLiveData<List<Match>> matches = new MediatorLiveData<>();
    private LiveData<List<Match>> currentDataSource;
    private final LiveData<List<Match>> favoriteMatches;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.matchRepository = new MatchRepository(application);
        // Lời gọi này giờ đã chính xác
        this.favoriteMatches = matchRepository.getAllFavoriteMatches();
    }

    public LiveData<List<Match>> getFavoriteMatches() {
        return favoriteMatches;
    }

    public void addFavorite(Match match) {
        matchRepository.addFavorite(match);
    }

    public void removeFavorite(Match match) {
        matchRepository.removeFavorite(match);
    }
    public void fetchMatchesForDate(String date) {
        LiveData<List<Match>> newDataSource = matchRepository.getMatchesByDateFromApi(date);

        if (currentDataSource != null) {
            matches.removeSource(currentDataSource);
        }

        currentDataSource = newDataSource;
        matches.addSource(currentDataSource, matchList -> {
            // --- THÊM LOG TẠI ĐÂY ---
            Log.d("ViewModelLog", "MediatorLiveData updated with " + (matchList != null ? matchList.size() : 0) + " matches.");

            matches.setValue(matchList);
        });
    }

    // Trả về LiveData, không thay đổi
    public LiveData<List<Match>> getMatches() {
        return matches;
    }
}