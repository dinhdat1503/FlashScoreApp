package com.example.flashscoreapp.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.flashscoreapp.data.model.Season;

public class LeagueDetailsPagerAdapter extends FragmentStateAdapter {
    private final int leagueId;
    private final Season season;

    public LeagueDetailsPagerAdapter(@NonNull Fragment fragment, int leagueId, Season season) {
        super(fragment);
        this.leagueId = leagueId;
        this.season = season;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Lấy ra năm của mùa giải một cách an toàn
        int seasonYear = (season != null) ? season.getYear() : 0;

        switch (position) {
            case 0:
                // Tab Bảng xếp hạng (chứa các tab con) cần `seasonYear`
                return StandingsContainerFragment.newInstance(leagueId, seasonYear);
            case 1:
                // SỬA Ở ĐÂY: Tab Kết quả cũng cần `seasonYear`
                return ResultsTabFragment.newInstance(leagueId, seasonYear);
            case 2:
                return new FixturesTabFragment(); // Tab Lịch thi đấu (tạm thời)
            default:
                // Mặc định trả về Fragment rỗng để tránh crash nếu getItemCount > 3
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        // SỬA Ở ĐÂY: Chúng ta chỉ có 3 tab chính
        return 3;
    }
}