package com.example.flashscoreapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import java.util.ArrayList;

public class OverallStandingsFragment extends Fragment {

    private StandingsViewModel standingsViewModel;
    private StandingsAdapter adapter;

    public static OverallStandingsFragment newInstance(int leagueId, int seasonYear) {
        OverallStandingsFragment fragment = new OverallStandingsFragment();
        Bundle args = new Bundle();
        args.putInt("LEAGUE_ID", leagueId);
        args.putInt("SEASON_YEAR", seasonYear);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Đảm bảo bạn đã đổi tên layout này
        return inflater.inflate(R.layout.fragment_overall_standings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int leagueId = 0;
        int seasonYear = 0;
        if (getArguments() != null) {
            leagueId = getArguments().getInt("LEAGUE_ID");
            seasonYear = getArguments().getInt("SEASON_YEAR");
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_standings);
        adapter = new StandingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        if (leagueId != 0 && seasonYear != 0) {
            StandingsViewModelFactory factory = new StandingsViewModelFactory(requireActivity().getApplication(), leagueId, seasonYear);
            standingsViewModel = new ViewModelProvider(this, factory).get(StandingsViewModel.class);
            observeViewModel();
        }
    }

    private void observeViewModel() {
        standingsViewModel.getStandings().observe(getViewLifecycleOwner(), standings -> {
            if (standings != null && !standings.isEmpty()) {
                adapter.setStandings(standings);
            } else {
                adapter.setStandings(new ArrayList<>());
            }
        });
    }
}