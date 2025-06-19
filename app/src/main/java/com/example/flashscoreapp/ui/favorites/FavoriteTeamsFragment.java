package com.example.flashscoreapp.ui.favorites;

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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Đảm bảo có import này

public class    FavoriteTeamsFragment extends Fragment {

    private FavoritesViewModel viewModel;
    private HomeGroupedAdapter groupedAdapter;
    private RecyclerView recyclerView;
    private TextView textNoItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_with_empty_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.main_recycler_view);
        textNoItems = view.findViewById(R.id.text_empty_message);
        textNoItems.setText("Chưa có trận đấu nào của đội yêu thích diễn ra hôm nay.");
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
            public void onFavoriteClick(Match match, boolean isFavorite) {}
        });

        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        viewModel.getFavoriteTeamMatches().observe(getViewLifecycleOwner(), matches -> {
            if (matches != null && !matches.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                textNoItems.setVisibility(View.GONE);
                List<Object> groupedList = groupMatchesByLeague(matches);
                groupedAdapter.setDisplayList(groupedList);
                groupedAdapter.setFavoriteMatchIds(new HashSet<>());
            } else {
                recyclerView.setVisibility(View.GONE);
                textNoItems.setVisibility(View.VISIBLE);
                groupedAdapter.setDisplayList(new ArrayList<>());
            }
        });
    }

    private List<Object> groupMatchesByLeague(List<Match> matches) {
        // Sửa lỗi ở dòng dưới đây
        Map<League, List<Match>> groupedMap = matches.stream()
                .collect(Collectors.groupingBy(
                        Match::getLeague,
                        LinkedHashMap::new,
                        Collectors.toList() // Đã sửa: Sử dụng Collectors.toList()
                ));
        List<Object> displayList = new ArrayList<>();
        for (Map.Entry<League, List<Match>> entry : groupedMap.entrySet()) {
            displayList.add(entry.getKey());
            displayList.addAll(entry.getValue());
        }
        return displayList;
    }
}