package com.example.flashscoreapp.ui.team_details;

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
import com.example.flashscoreapp.data.model.domain.League;
import com.example.flashscoreapp.data.model.domain.Match;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeamMatchesFragment extends Fragment {

    private static final String ARG_IS_RESULTS = "arg_is_results";
    private boolean isResults;
    private TeamDetailsViewModel viewModel;
    private TeamMatchesGroupedAdapter adapter;

    public static TeamMatchesFragment newInstance(boolean isResults) {
        TeamMatchesFragment fragment = new TeamMatchesFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_IS_RESULTS, isResults);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isResults = getArguments().getBoolean(ARG_IS_RESULTS);
        }
        viewModel = new ViewModelProvider(requireActivity()).get(TeamDetailsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view_only, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int teamId = requireActivity().getIntent().getIntExtra(TeamDetailsActivity.EXTRA_TEAM_ID, 0);
        adapter = new TeamMatchesGroupedAdapter(teamId);
        recyclerView.setAdapter(adapter);

        viewModel.getMatchesForTeam().observe(getViewLifecycleOwner(), allMatches -> {
            if (allMatches != null) {
                List<Match> filteredAndSortedMatches;

                if (isResults) {
                    // Lọc và sắp xếp các trận KẾT QUẢ (chỉ lấy những trận đã kết thúc)
                    filteredAndSortedMatches = allMatches.stream()
                            .filter(match -> "FT".equals(match.getStatus()))
                            .sorted(Comparator.comparingLong(Match::getMatchTime).reversed())
                            .collect(Collectors.toList());
                } else {
                    // Lọc và sắp xếp các trận LỊCH THI ĐẤU (lấy tất cả những trận chưa kết thúc)
                    filteredAndSortedMatches = allMatches.stream()
                            .filter(match -> !"FT".equals(match.getStatus()))
                            .sorted(Comparator.comparingLong(Match::getMatchTime))
                            .collect(Collectors.toList());
                }

                if (!filteredAndSortedMatches.isEmpty()) {
                    List<Object> groupedList = groupMatchesByLeague(filteredAndSortedMatches);
                    adapter.setDisplayList(groupedList);
                } else {
                    adapter.setDisplayList(new ArrayList<>());
                }
            } else {
                adapter.setDisplayList(new ArrayList<>());
            }
        });
    }

    private List<Object> groupMatchesByLeague(List<Match> matches) {
        Map<League, List<Match>> groupedMap = matches.stream()
                .collect(Collectors.groupingBy(
                        Match::getLeague,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        List<Object> displayList = new ArrayList<>();
        for (Map.Entry<League, List<Match>> entry : groupedMap.entrySet()) {
            displayList.add(entry.getKey());
            displayList.addAll(entry.getValue());
        }
        return displayList;
    }
}