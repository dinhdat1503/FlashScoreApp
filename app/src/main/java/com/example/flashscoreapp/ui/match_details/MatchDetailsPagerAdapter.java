package com.example.flashscoreapp.ui.match_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flashscoreapp.ui.leagues.details.fixtures.FixturesTabFragment; // <<-- THÊM IMPORT NÀY
import com.example.flashscoreapp.ui.match_details.statistics.MatchStatisticsFragment;
import com.example.flashscoreapp.ui.match_details.summary.MatchSummaryFragment;

public class MatchDetailsPagerAdapter extends FragmentStateAdapter {

    public MatchDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MatchSummaryFragment();
            case 1:
                return new MatchStatisticsFragment();
            case 2: // Đội hình (chưa có)
            case 3: // Đối đầu (chưa có)
            case 4: // Bảng xếp hạng (chưa có)
            default:
                // Trả về một Fragment tạm thời thông báo tính năng đang phát triển
                return new FixturesTabFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5; // Số lượng tab bạn muốn có
    }
}