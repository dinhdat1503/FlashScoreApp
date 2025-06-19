package com.example.flashscoreapp.ui.team_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.flashscoreapp.ui.team_details.squad.TeamSquadFragment;

/**
 * Adapter cho ViewPager2, quản lý các Fragment (tabs) trong màn hình chi tiết đội bóng.
 */
public final class TeamDetailsPagerAdapter extends FragmentStateAdapter {

    // Giả sử có 4 tab, chúng ta sẽ cập nhật sau
    private static final int NUM_TABS = 1;

    public TeamDetailsPagerAdapter(@NonNull final FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(final int position) {
        switch (position) {
            case 0:
            default:
                return new TeamSquadFragment();
            // case 1: return new TeamResultsFragment();
            // case 2: return new TeamFixturesFragment();
            // case 3: return new TeamStandingsFragment();
        }
    }

    @Override
    public int getItemCount() {
        // Hiện tại chỉ có 1 tab, chúng ta sẽ tăng số này lên sau
        return NUM_TABS;
    }
}
