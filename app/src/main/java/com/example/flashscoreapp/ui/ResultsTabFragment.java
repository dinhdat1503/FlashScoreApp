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

public class ResultsTabFragment extends Fragment {
    private com.example.flashscoreapp.ui.ResultsViewModel viewModel;
    private RecyclerView recyclerView;
    private ResultsAdapter adapter;

    public static ResultsTabFragment newInstance(int leagueId, int season) {
        ResultsTabFragment fragment = new ResultsTabFragment();
        Bundle args = new Bundle();
        args.putInt("LEAGUE_ID", leagueId);
        args.putInt("SEASON", season);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Tạo một layout đơn giản cho Fragment này
        return inflater.inflate(R.layout.fragment_recycler_view_only, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int leagueId = getArguments().getInt("LEAGUE_ID");
        int season = getArguments().getInt("SEASON");

        recyclerView = view.findViewById(R.id.main_recycler_view);
        adapter = new ResultsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnMatchClickListener(match -> {
            Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
            intent.putExtra("EXTRA_MATCH", match);
            startActivity(intent);
        });

        ResultsViewModelFactory factory = new ResultsViewModelFactory(getActivity().getApplication(), leagueId, season);
        viewModel = new ViewModelProvider(this, factory).get(com.example.flashscoreapp.ui.ResultsViewModel.class);

        viewModel.getGroupedResults().observe(getViewLifecycleOwner(), items -> {
            if (items != null) {
                adapter.setItems(items);
            }
        });
    }
}