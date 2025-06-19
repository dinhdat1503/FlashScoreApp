package com.example.flashscoreapp.ui.teamdetails;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.bumptech.glide.Glide;
import com.example.flashscoreapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.Calendar;

public class TeamDetailsActivity extends AppCompatActivity {

    private TeamDetailsViewModel viewModel;
    private int teamId;
    private String teamName;
    private String teamLogoUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        // 1. Lấy dữ liệu từ Intent
        teamId = getIntent().getIntExtra("TEAM_ID", 0);
        teamName = getIntent().getStringExtra("TEAM_NAME");
        teamLogoUrl = getIntent().getStringExtra("TEAM_LOGO");

        if (teamId == 0) {
            finish(); // Đóng activity nếu không có teamId
            return;
        }

        // 2. Cập nhật giao diện Header và Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_team_details);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(teamName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageView teamLogoImageView = findViewById(R.id.image_team_logo_details);
        TextView teamNameTextView = findViewById(R.id.text_team_name_details);

        teamNameTextView.setText(teamName);
        Glide.with(this).load(teamLogoUrl).placeholder(R.drawable.ic_leagues_24).into(teamLogoImageView);

        // 3. Khởi tạo ViewModel
        int currentSeasonYear = Calendar.getInstance().get(Calendar.YEAR) - 1; // Lấy mùa giải gần nhất, ví dụ 2023
        TeamDetailsViewModelFactory factory = new TeamDetailsViewModelFactory(getApplication(), teamId, currentSeasonYear);
        viewModel = new ViewModelProvider(this, factory).get(TeamDetailsViewModel.class);

        // 4. Thiết lập ViewPager và TabLayout
        ViewPager2 viewPager = findViewById(R.id.view_pager_team_details);
        TabLayout tabLayout = findViewById(R.id.tab_layout_team_details);

        TeamDetailsPagerAdapter pagerAdapter = new TeamDetailsPagerAdapter(this, viewModel);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("KẾT QUẢ");
                    break;
                case 1:
                    tab.setText("LỊCH THI ĐẤU");
                    break;
                case 2:
                    tab.setText("ĐỘI HÌNH");
                    break;
            }
        }).attach();

        // 5. Observe dữ liệu từ ViewModel và truyền vào Adapter
        viewModel.getMatchesForTeam().observe(this, matches -> {
            pagerAdapter.setMatches(matches);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}