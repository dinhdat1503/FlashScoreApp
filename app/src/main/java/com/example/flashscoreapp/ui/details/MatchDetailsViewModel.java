package com.example.flashscoreapp.ui.details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.model.MatchDetails;
import com.example.flashscoreapp.data.repository.MatchRepository;

public class MatchDetailsViewModel extends AndroidViewModel {

    private final MatchRepository repository;
    private final LiveData<MatchDetails> matchDetails;

    public MatchDetailsViewModel(@NonNull Application application, int matchId) {
        super(application);
        // Sửa lại dòng này để truyền application vào constructor của Repository
        repository = new MatchRepository(application);
        matchDetails = repository.getMatchDetailsFromApi(matchId);
    }

    public LiveData<MatchDetails> getMatchDetails() {
        return matchDetails;
    }
}