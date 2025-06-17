package com.example.flashscoreapp.ui.favorites;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

    private final MatchRepository repository;
    private final LiveData<List<Match>> favoriteMatches;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        repository = new MatchRepository(application);
        favoriteMatches = repository.getAllFavoriteMatches();
    }

    public LiveData<List<Match>> getFavoriteMatches() {
        return favoriteMatches;
    }

    // Người dùng chỉ có thể bỏ yêu thích từ màn hình này
    public void removeFavorite(Match match) {
        repository.removeFavorite(match);
    }
}