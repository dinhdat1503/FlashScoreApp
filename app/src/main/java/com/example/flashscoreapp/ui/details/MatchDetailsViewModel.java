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
        repository = new MatchRepository();
        // --- THAY ĐỔI DUY NHẤT TẠI ĐÂY ---
        // Gọi phương thức mới để lấy dữ liệu từ API thay vì từ mock
        matchDetails = repository.getMatchDetailsFromApi(matchId);
    }

    public LiveData<MatchDetails> getMatchDetails() {
        return matchDetails;
    }
}