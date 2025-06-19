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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TeamMatchesFragment extends Fragment {

    private static final String ARG_MATCHES = "arg_matches";
    private List<Match> matches;
    private TeamDetailsViewModel viewModel;

    public static TeamMatchesFragment newInstance(List<Match> matches) {
        TeamMatchesFragment fragment = new TeamMatchesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MATCHES, (Serializable) matches);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            matches = (List<Match>) getArguments().getSerializable(ARG_MATCHES);
        }
        // Lấy ViewModel từ Activity cha
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

        // Lấy teamId từ Activity để truyền vào Adapter
        int teamId = requireActivity().getIntent().getIntExtra(TeamDetailsActivity.EXTRA_TEAM_ID, 0);
        TeamMatchesGroupedAdapter adapter = new TeamMatchesGroupedAdapter(teamId);
        recyclerView.setAdapter(adapter);

        if (matches != null && !matches.isEmpty()) {
            List<Object> groupedList = groupMatchesByLeague(matches);
            adapter.setDisplayList(groupedList);
        }
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