package com.example.flashscoreapp.ui.home;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final MatchRepository matchRepository;
    private final MutableLiveData<List<Match>> matches = new MutableLiveData<>();

    public HomeViewModel(Application application) {
        super(application);
        this.matchRepository = new MatchRepository();
    }

    // Phương thức này giờ sẽ hoạt động đúng như mong đợi
    public void fetchMatchesForDate(String date) {
        // Repository sẽ xử lý việc tìm đúng ngày, ViewModel không cần quan tâm
        matchRepository.getMatchesByDateFromMock(getApplication().getApplicationContext(), date).observeForever(matchList -> {
            matches.setValue(matchList);
        });
    }

    public LiveData<List<Match>> getMatches() {
        return matches;
    }
}