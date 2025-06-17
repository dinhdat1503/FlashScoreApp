package com.example.flashscoreapp.ui.match_details;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
// SỬA LỖI: Import đúng lớp Toolbar từ thư viện androidx
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.Match;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MatchDetailsActivity extends AppCompatActivity {
    private MatchDetailsViewModel viewModel;
    private Match match;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);

        // SỬA LỖI: Sử dụng đúng kiểu androidx.appcompat.widget.Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        match = (Match) getIntent().getSerializableExtra("EXTRA_MATCH");
        if (match == null) {
            Toast.makeText(this, "Lỗi: không có dữ liệu trận đấu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        MatchDetailsViewModelFactory factory = new MatchDetailsViewModelFactory(getApplication(), match.getMatchId());
        viewModel = new ViewModelProvider(this, factory).get(MatchDetailsViewModel.class);

        updateScoreboard();

        ViewPager2 viewPager = findViewById(R.id.view_pager_match_details);
        TabLayout tabLayout = findViewById(R.id.tab_layout_match_details);

        viewPager.setAdapter(new MatchDetailsPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("TÓM TẮT"); break;
                case 1: tab.setText("SỐ LIỆU"); break;
                case 2: tab.setText("ĐỘI HÌNH"); break;
                case 3: tab.setText("ĐỐI ĐẦU"); break;
                case 4: tab.setText("BẢNG XẾP HẠNG"); break;
            }
        }).attach();

        observeMatchInfo();
    }

    private void updateScoreboard() {
        TextView homeName = findViewById(R.id.text_home_name_details);
        TextView awayName = findViewById(R.id.text_away_name_details);
        TextView score = findViewById(R.id.text_score_details);
        ImageView homeLogo = findViewById(R.id.image_home_logo_details);
        ImageView awayLogo = findViewById(R.id.image_away_logo_details);

        homeName.setText(match.getHomeTeam().getName());
        awayName.setText(match.getAwayTeam().getName());
        score.setText(match.getScore().getHome() + " - " + match.getScore().getAway());

        Glide.with(this).load(match.getHomeTeam().getLogoUrl()).into(homeLogo);
        Glide.with(this).load(match.getAwayTeam().getLogoUrl()).into(awayLogo);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void observeMatchInfo() {
        final TextView textReferee = findViewById(R.id.text_referee);
        final TextView textStadium = findViewById(R.id.text_stadium);

        viewModel.getMatchDetails().observe(this, details -> {
            if (details != null) {
                if (details.getReferee() != null && !details.getReferee().isEmpty()) {
                    textReferee.setText(details.getReferee());
                } else {
                    textReferee.setText("N/A");
                }

                if (details.getStadium() != null && !details.getStadium().isEmpty()) {
                    textStadium.setText(details.getStadium());
                } else {
                    textStadium.setText("N/A");
                }
            }
        });
    }

    public Match getMatch() { return this.match; }
}