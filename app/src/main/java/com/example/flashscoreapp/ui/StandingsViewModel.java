package com.example.flashscoreapp.ui;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.flashscoreapp.data.model.StandingItem;
import com.example.flashscoreapp.data.repository.MatchRepository;
import java.util.List;

public class StandingsViewModel extends AndroidViewModel {
    private final MatchRepository repository;
    private final int leagueId;
    // Đổi LiveData thành MutableLiveData để có thể cập nhật giá trị
    private final MutableLiveData<List<StandingItem>> standings = new MutableLiveData<>();

    // Xóa season khỏi constructor
    public StandingsViewModel(@NonNull Application application, int leagueId) {
        super(application);
        this.repository = new MatchRepository(application);
        this.leagueId = leagueId;
    }

    public LiveData<List<StandingItem>> getStandings() {
        return standings;
    }

    // Tạo phương thức mới để tải dữ liệu khi người dùng chọn mùa giải
    public void loadStandingsForSeason(int season) {
        // Lấy LiveData từ repository và quan sát nó
        LiveData<List<StandingItem>> repoData = repository.getStandings(leagueId, season);
        repoData.observeForever(new Observer<List<StandingItem>>() {
            @Override
            public void onChanged(List<StandingItem> standingItems) {
                standings.postValue(standingItems);
                // Hủy observer sau khi nhận dữ liệu để tránh leak
                repoData.removeObserver(this);
            }
        });
    }
}

// Lưu ý: Bạn sẽ cần thêm import cho Observer: import androidx.lifecycle.Observer;