package com.example.flashscoreapp.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StandingsContainerPagerAdapter extends FragmentStateAdapter {
    private int leagueId;
    private int seasonYear;

    public StandingsContainerPagerAdapter(@NonNull Fragment fragment, int leagueId, int seasonYear) {
        super(fragment);
        this.leagueId = leagueId;
        this.seasonYear = seasonYear;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return TopScorersFragment.newInstance(leagueId, seasonYear);
            // Thêm case cho Sân nhà, sân khách sau
            case 0:
            default:
                return OverallStandingsFragment.newInstance(leagueId, seasonYear);
        }
    }

    @Override
    public int getItemCount() {
        return 4; // TOÀN THỂ, VUA PHÁ LƯỚI, SÂN NHÀ, SÂN KHÁCH
    }
}