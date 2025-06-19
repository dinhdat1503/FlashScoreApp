package com.example.flashscoreapp.ui.favorites;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FavoritesPagerAdapter extends FragmentStateAdapter {

    public FavoritesPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new FavoriteTeamsFragment();
        }
        return new FavoriteMatchesFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}