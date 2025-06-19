package com.example.flashscoreapp.ui.teamdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.ui.home.MatchAdapter;
import com.example.flashscoreapp.ui.match_details.MatchDetailsActivity;
import java.io.Serializable;
import java.util.List;

public class TeamMatchesFragment extends Fragment {

    private static final String ARG_MATCHES = "arg_matches";
    private List<Match> matches;

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
        MatchAdapter adapter = new MatchAdapter(); // Tái sử dụng MatchAdapter
        recyclerView.setAdapter(adapter);
        adapter.setMatches(matches);

        adapter.setOnItemClickListener(match -> {
            Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
            intent.putExtra("EXTRA_MATCH", match);
            startActivity(intent);
        });
    }
}