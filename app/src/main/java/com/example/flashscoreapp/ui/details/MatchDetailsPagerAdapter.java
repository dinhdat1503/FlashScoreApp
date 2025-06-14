package com.example.flashscoreapp.ui.details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MatchDetailsPagerAdapter extends FragmentStateAdapter {

    public MatchDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new MatchStatisticsFragment();
            // Thêm các case khác ở đây
            case 0:
            default:
                return new MatchSummaryFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5; // Số lượng tab bạn muốn có
    }
}