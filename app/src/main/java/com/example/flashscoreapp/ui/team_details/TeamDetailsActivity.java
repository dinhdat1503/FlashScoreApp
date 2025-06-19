package com.example.flashscoreapp.ui.team_details;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.flashscoreapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Activity chính để hiển thị màn hình chi tiết đội bóng với các tab thông tin.
 */
public final class TeamDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_TEAM_ID = "EXTRA_TEAM_ID";
    public static final String EXTRA_TEAM_NAME = "EXTRA_TEAM_NAME";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        final int teamId = getIntent().getIntExtra(EXTRA_TEAM_ID, -1);
        final String teamName = getIntent().getStringExtra(EXTRA_TEAM_NAME);

        if (teamId == -1) {
            // Không có ID hợp lệ, không thể tiếp tục.
            finish();
            return;
        }

        setupToolbar(teamName);
        setupViewModel(teamId);
        setupViewPagerAndTabs();
    }

    private void setupToolbar(final String teamName) {
        final Toolbar toolbar = findViewById(R.id.toolbar_team_details);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(teamName != null ? teamName : "Chi tiết đội bóng");
        }
        toolbar.setNavigationOnClickListener(v -> finish()); // Sử dụng finish() thay vì onBackPressed()
    }

    private void setupViewModel(final int teamId) {
        final TeamDetailsViewModelFactory factory = new TeamDetailsViewModelFactory(teamId);
        // ViewModel được gắn với vòng đời của Activity này.
        final TeamDetailsViewModel viewModel = new ViewModelProvider(this, factory).get(TeamDetailsViewModel.class);
    }

    private void setupViewPagerAndTabs() {
        final ViewPager2 viewPager = findViewById(R.id.view_pager_team_details);
        final TabLayout tabLayout = findViewById(R.id.tab_layout_team_details);
        final TeamDetailsPagerAdapter pagerAdapter = new TeamDetailsPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Kết nối TabLayout với ViewPager
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(getTabTitle(position));
        }).attach();
    }

    private String getTabTitle(final int position) {
        switch (position) {
            case 0:
            default:
                return "Đội hình";
            // case 1: return "Kết quả";
            // case 2: return "Lịch thi đấu";
            // case 3: return "Bảng xếp hạng";
        }
    }
}
