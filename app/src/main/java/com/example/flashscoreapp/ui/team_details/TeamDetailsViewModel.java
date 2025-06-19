package com.example.flashscoreapp.ui.team_details;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.flashscoreapp.data.api.RetrofitClient;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.Player;
import com.example.flashscoreapp.data.repository.MatchRepository;
import com.example.flashscoreapp.data.repository.TeamRepository;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// --- SỬA TOÀN BỘ FILE NÀY ---
public class TeamDetailsViewModel extends AndroidViewModel {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final LiveData<List<Match>> matchesForTeam;
    private final LiveData<List<Player>> squad;

    // Bỏ seasonYear, chỉ giữ lại teamId
    public TeamDetailsViewModel(@NonNull Application application, int teamId) {
        super(application);
        matchRepository = new MatchRepository(application);
        teamRepository = new TeamRepository(RetrofitClient.getApiService());

        // Tự tính toán khoảng thời gian
        // Ví dụ: Lấy từ 6 tháng trước đến 2 tháng sau
        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        // Tính ngày kết thúc (2 tháng sau)
        calendar.add(Calendar.MONTH, 2);
        String toDate = apiDateFormat.format(calendar.getTime());

        // Tính ngày bắt đầu (lùi lại 8 tháng từ ngày kết thúc để có khoảng 6 tháng trước)
        calendar.add(Calendar.MONTH, -8);
        String fromDate = apiDateFormat.format(calendar.getTime());

        // Gọi repository với khoảng thời gian đã tính
        matchesForTeam = matchRepository.getMatchesForTeam(teamId, fromDate, toDate);
        squad = teamRepository.getSquad(teamId);
    }

    public LiveData<List<Match>> getMatchesForTeam() {
        return matchesForTeam;
    }

    public LiveData<List<Player>> getSquad() {
        return squad;
    }
}