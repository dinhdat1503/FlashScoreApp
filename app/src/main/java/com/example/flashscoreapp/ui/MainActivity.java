package com.example.flashscoreapp.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.flashscoreapp.R;
import com.example.flashscoreapp.ui.favorites.FavoritesFragment;
import com.example.flashscoreapp.ui.home.HomeFragment;
import com.example.flashscoreapp.ui.leagues.LeaguesFragment;
import com.example.flashscoreapp.ui.live.LiveMatchesFragment;
import com.example.flashscoreapp.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Đặt listener cho việc chọn item trên bottom navigation
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
                toolbar.setTitle("Today's Matches");
            } else if (itemId == R.id.navigation_leagues) {
                selectedFragment = new LeaguesFragment();
                toolbar.setTitle("Leagues");
            } else if (itemId == R.id.navigation_favorites) {
                selectedFragment = new FavoritesFragment();
                toolbar.setTitle("Favorites");
            } else if (itemId == R.id.navigation_live) {
                selectedFragment = new LiveMatchesFragment();
                toolbar.setTitle("Trực tiếp");
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        // Đặt HomeFragment làm màn hình mặc định khi ứng dụng khởi chạy
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            toolbar.setTitle("Today's Matches");
        }
    }

    // Hàm phụ trợ để thay thế Fragment
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}