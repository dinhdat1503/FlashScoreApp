package com.example.flashscoreapp.ui.teamdetails;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.ui.leagues.details.fixtures.FixturesTabFragment; // Tái sử dụng làm placeholder
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamDetailsPagerAdapter extends FragmentStateAdapter {

    private List<Match> allMatches = new ArrayList<>();
    private final TeamDetailsViewModel viewModel;

    public TeamDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity, TeamDetailsViewModel viewModel) {
        super(fragmentActivity);
        this.viewModel = viewModel;
    }

    public void setMatches(List<Match> matches) {
        if (matches == null) {
            this.allMatches = new ArrayList<>();
        } else {
            this.allMatches = matches;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: // Kết quả
                List<Match> results = allMatches.stream()
                        .filter(match -> "FT".equals(match.getStatus()))
                        .collect(Collectors.toList());
                return TeamMatchesFragment.newInstance(results);
            case 1: // Lịch thi đấu
                List<Match> fixtures = allMatches.stream()
                        .filter(match -> !"FT".equals(match.getStatus()))
                        .collect(Collectors.toList());
                return TeamMatchesFragment.newInstance(fixtures);
            case 2: // Đội hình
            default:
                // Tạm thời dùng fragment placeholder cho các tính năng chưa làm
                return new FixturesTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Kết quả, Lịch thi đấu, Đội hình
    }
}