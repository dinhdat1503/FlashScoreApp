package com.example.flashscoreapp.ui.team_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.ui.team_details.squad.TeamSquadFragment;
// SỬA LẠI DÒNG IMPORT DƯỚI ĐÂY
import com.example.flashscoreapp.ui.team_details.TeamDetailsViewModel;

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
            case 0: // Tab "KẾT QUẢ"
                List<Match> results = allMatches.stream()
                        .filter(match -> "FT".equals(match.getStatus()))
                        .collect(Collectors.toList());
                return TeamMatchesFragment.newInstance(results);
            case 1: // Tab "LỊCH THI ĐẤU"
                List<Match> fixtures = allMatches.stream()
                        .filter(match -> !"FT".equals(match.getStatus()))
                        .collect(Collectors.toList());
                return TeamMatchesFragment.newInstance(fixtures);
            case 2: // Tab "ĐỘI HÌNH"
                return new TeamSquadFragment();
            default:
                // Trả về một Fragment trống để tránh crash
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Chúng ta có 3 tab: Kết quả, Lịch thi đấu, Đội hình
    }
}