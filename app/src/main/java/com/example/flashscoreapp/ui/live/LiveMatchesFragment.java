package com.example.flashscoreapp.ui.live;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.League;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.ui.home.HomeGroupedAdapter;
import com.example.flashscoreapp.ui.home.MatchAdapter;
import com.example.flashscoreapp.ui.match_details.MatchDetailsActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LiveMatchesFragment extends Fragment {

    private LiveMatchesViewModel liveMatchesViewModel;
    private HomeGroupedAdapter groupedAdapter;
    private RecyclerView recyclerView;
    private TextView textNoMatches;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_with_empty_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.main_recycler_view);
        textNoMatches = view.findViewById(R.id.text_empty_message);
        textNoMatches.setText("Không có trận đấu nào đang trực tiếp.");

        setupRecyclerView();

        liveMatchesViewModel = new ViewModelProvider(this).get(LiveMatchesViewModel.class);
        observeViewModel();
    }

    private void setupRecyclerView() {
        groupedAdapter = new HomeGroupedAdapter();
        recyclerView.setAdapter(groupedAdapter);
        groupedAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Match match) {
                Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
                intent.putExtra("EXTRA_MATCH", match);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Match match, boolean isFavorite) {
                // Có thể thêm logic xử lý Yêu thích ở đây nếu cần
            }
        });
    }

    private void observeViewModel() {
        textNoMatches.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        liveMatchesViewModel.getLiveMatches().observe(getViewLifecycleOwner(), matches -> {
            if (matches != null && !matches.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                textNoMatches.setVisibility(View.GONE);
                List<Object> groupedList = groupMatchesByLeague(matches);
                groupedAdapter.setDisplayList(groupedList);
            } else {
                recyclerView.setVisibility(View.GONE);
                textNoMatches.setVisibility(View.VISIBLE);
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