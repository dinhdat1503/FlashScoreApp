package com.example.flashscoreapp.ui;

import android.content.Intent;
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
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.ui.details.MatchDetailsActivity;
import com.example.flashscoreapp.ui.home.MatchAdapter;

public class ResultsTabFragment extends Fragment {

    private ResultsViewModel viewModel;
    private ResultsAdapter resultsAdapter;

    public static ResultsTabFragment newInstance(int leagueId, int seasonYear) {
        ResultsTabFragment fragment = new ResultsTabFragment();
        Bundle args = new Bundle();
        args.putInt("LEAGUE_ID", leagueId);
        args.putInt("SEASON_YEAR", seasonYear);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view_only, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int leagueId = getArguments() != null ? getArguments().getInt("LEAGUE_ID") : 0;
        int seasonYear = getArguments() != null ? getArguments().getInt("SEASON_YEAR") : 0;

        RecyclerView recyclerView = view.findViewById(R.id.main_recycler_view);
        resultsAdapter = new ResultsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(resultsAdapter);

        resultsAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Match match) {
                Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
                intent.putExtra("EXTRA_MATCH", match);
                startActivity(intent);
            }
            @Override
            public void onFavoriteClick(Match match, boolean isFavorite) {
                // Tạm thời không xử lý
            }
        });

        if (leagueId != 0 && seasonYear != 0) {
            ResultsViewModelFactory factory = new ResultsViewModelFactory(requireActivity().getApplication(), leagueId, seasonYear);
            viewModel = new ViewModelProvider(this, factory).get(ResultsViewModel.class);

            viewModel.getGroupedResults().observe(getViewLifecycleOwner(), items -> {
                if (items != null) {
                    resultsAdapter.setItems(items);
                }
            });
        }
    }
}