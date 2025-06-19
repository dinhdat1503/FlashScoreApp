package com.example.flashscoreapp.ui.team_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.flashscoreapp.ui.team_details.squad.TeamSquadFragment;

public class TeamDetailsPagerAdapter extends FragmentStateAdapter {

    public TeamDetailsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: // Tab "KẾT QUẢ"
                // Truyền cờ `true` để fragment biết cần hiển thị kết quả (trận đã đá)
                return TeamMatchesFragment.newInstance(true);
            case 1: // Tab "LỊCH THI ĐẤU"
                // Truyền cờ `false` để fragment biết cần hiển thị lịch thi đấu (trận sắp đá)
                return TeamMatchesFragment.newInstance(false);
            case 2: // Tab "ĐỘI HÌNH"
                return new TeamSquadFragment();
            default:
                // Trả về một Fragment trống để tránh lỗi
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Chúng ta có 3 tab: Kết quả, Lịch thi đấu, Đội hình
    }
}