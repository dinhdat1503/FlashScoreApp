package com.example.flashscoreapp.ui.team_details;

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

    public static final String EXTRA_TEAM_ID = "TEAM_ID";
    public static final String EXTRA_TEAM_NAME = "TEAM_NAME";
    public static final String EXTRA_TEAM_LOGO = "TEAM_LOGO";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        teamId = getIntent().getIntExtra(EXTRA_TEAM_ID, 0);
        teamName = getIntent().getStringExtra(EXTRA_TEAM_NAME);
        teamLogoUrl = getIntent().getStringExtra(EXTRA_TEAM_LOGO);

        if (teamId == 0) {
            finish();
            return;
        }

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

        // ⭐ SỬA LỖI Ở ĐÂY: Lấy năm hiện tại, không trừ đi 1
        int currentSeasonYear = Calendar.getInstance().get(Calendar.YEAR);

        TeamDetailsViewModelFactory factory = new TeamDetailsViewModelFactory(getApplication(), teamId, currentSeasonYear);
        viewModel = new ViewModelProvider(this, factory).get(TeamDetailsViewModel.class);

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

        viewModel.getMatchesForTeam().observe(this, pagerAdapter::setMatches);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}