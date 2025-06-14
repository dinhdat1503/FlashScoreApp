package com.example.flashscoreapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView; // Thêm import này

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide; // Thêm import này
import com.example.flashscoreapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LeagueDetailsFragment extends Fragment {

    private int leagueId;
    private String leagueName;
    private String leagueLogoUrl;
    private int selectedSeason;

    public static LeagueDetailsFragment newInstance(int leagueId, String leagueName, String leagueLogoUrl) {
        LeagueDetailsFragment fragment = new LeagueDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("LEAGUE_ID", leagueId);
        args.putString("LEAGUE_NAME", leagueName);
        args.putString("LEAGUE_LOGO_URL", leagueLogoUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            leagueId = getArguments().getInt("LEAGUE_ID");
            leagueName = getArguments().getString("LEAGUE_NAME");
            leagueLogoUrl = getArguments().getString("LEAGUE_LOGO_URL");
            selectedSeason = Calendar.getInstance().get(Calendar.YEAR) - 1;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        return inflater.inflate(R.layout.fragment_league_details, c, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ các view từ layout
        ImageView leagueLogoImageView = view.findViewById(R.id.image_league_logo);
        TextView leagueNameTextView = view.findViewById(R.id.text_league_name_header);
        Spinner seasonSpinner = view.findViewById(R.id.spinner_season);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout_main);
        ViewPager2 viewPager = view.findViewById(R.id.view_pager_main);


        leagueNameTextView.setText(leagueName);
        Glide.with(this)
                .load(leagueLogoUrl)
                .placeholder(R.drawable.ic_leagues_24)
                .error(R.drawable.ic_leagues_24)
                .into(leagueLogoImageView);


        // Setup Spinner mùa giải
        setupSeasonSpinner(seasonSpinner, viewPager);

        // Setup ViewPager và TabLayout
        viewPager.setAdapter(new LeagueDetailsPagerAdapter(this, leagueId, selectedSeason));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("BẢNG XẾP HẠNG"); break;
                case 1: tab.setText("KẾT QUẢ"); break;
                case 2: tab.setText("LỊCH THI ĐẤU"); break;
            }
        }).attach();
    }

    private void setupSeasonSpinner(Spinner spinner, ViewPager2 viewPager) {
        List<String> seasons = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            int seasonStartYear = currentYear - 1 - i;
            seasons.add(seasonStartYear + "/" + (seasonStartYear + 1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, seasons);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                selectedSeason = Integer.parseInt(selected.substring(0, 4));
                viewPager.setAdapter(new LeagueDetailsPagerAdapter(LeagueDetailsFragment.this, leagueId, selectedSeason));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}