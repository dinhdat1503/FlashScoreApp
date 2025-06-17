package com.example.flashscoreapp.ui.leaguedetails.results;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.RoundHeader;
import com.example.flashscoreapp.data.repository.MatchRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResultsViewModel extends AndroidViewModel {
    private final MatchRepository repository;
    private final MediatorLiveData<List<Object>> groupedResults = new MediatorLiveData<>();

    public ResultsViewModel(@NonNull Application application, int leagueId, int seasonYear) {
        super(application);
        repository = new MatchRepository(application);
        loadResults(leagueId, seasonYear);
    }

    public LiveData<List<Object>> getGroupedResults() {
        return groupedResults;
    }

    private void loadResults(int leagueId, int seasonYear) {
        LiveData<List<Match>> source = repository.getResultsForLeague(leagueId, seasonYear);
        groupedResults.addSource(source, matches -> {
            if (matches != null) {
                groupedResults.postValue(groupAndSortMatches(matches));
            } else {
                groupedResults.postValue(new ArrayList<>());
            }
        });
    }

    private List<Object> groupAndSortMatches(List<Match> matches) {
        Map<String, List<Match>> groupedByRound = matches.stream()
                .collect(Collectors.groupingBy(Match::getRound));

        List<String> sortedRounds = new ArrayList<>(groupedByRound.keySet());

        // Sắp xếp các vòng đấu theo SỐ thứ tự giảm dần
        Collections.sort(sortedRounds, (round1, round2) -> {
            try {
                // Trích xuất số từ chuỗi (ví dụ: "Regular Season - 38" -> 38)
                int r1 = Integer.parseInt(round1.replaceAll("\\D+", ""));
                int r2 = Integer.parseInt(round2.replaceAll("\\D+", ""));
                // So sánh ngược để có thứ tự giảm dần (r2 so với r1)
                return Integer.compare(r2, r1);
            } catch (NumberFormatException e) {
                // Nếu không có số, sắp xếp theo chữ cái
                return round2.compareTo(round1);
            }
        });

        List<Object> flattenedList = new ArrayList<>();
        for (String round : sortedRounds) {
            String translatedRound = round.replace("Regular Season -", "Vòng");
            flattenedList.add(new RoundHeader(translatedRound.trim()));
            List<Match> matchesInRound = groupedByRound.get(round);
            if (matchesInRound != null) {
                // Sắp xếp các trận đấu trong vòng theo thời gian giảm dần
                matchesInRound.sort(Comparator.comparingLong(Match::getMatchTime).reversed());
                flattenedList.addAll(matchesInRound);
            }
        }
        return flattenedList;
    }
}