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
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.ui.match_details.MatchDetailsActivity;
import com.example.flashscoreapp.ui.home.MatchAdapter;
import java.util.HashSet;
import java.util.stream.Collectors;

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private RecyclerView recyclerViewFavorites;
    private MatchAdapter matchAdapter;
    private TextView textNoFavorites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout mới của chúng ta
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Khởi tạo Views
        recyclerViewFavorites = view.findViewById(R.id.recycler_view_favorites);
        textNoFavorites = view.findViewById(R.id.text_no_favorites);

        // Khởi tạo ViewModel
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        setupRecyclerView();
        observeViewModel();
    }

    private void setupRecyclerView() {
        matchAdapter = new MatchAdapter();
        recyclerViewFavorites.setAdapter(matchAdapter);

        matchAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Match match) {
                Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
                intent.putExtra("EXTRA_MATCH", match);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Match match, boolean isFavorite) {
                // Ở màn hình yêu thích, nhấn vào ngôi sao chỉ có thể là BỎ yêu thích
                if (isFavorite) {
                    favoritesViewModel.removeFavorite(match);
                }
            }
        });
    }

    private void observeViewModel() {
        favoritesViewModel.getFavoriteMatches().observe(getViewLifecycleOwner(), favoriteMatches -> {
            if (favoriteMatches == null || favoriteMatches.isEmpty()) {
                // Nếu danh sách rỗng, hiện text và ẩn RecyclerView
                recyclerViewFavorites.setVisibility(View.GONE);
                textNoFavorites.setVisibility(View.VISIBLE);
            } else {
                // Nếu có dữ liệu, hiện RecyclerView và ẩn text
                recyclerViewFavorites.setVisibility(View.VISIBLE);
                textNoFavorites.setVisibility(View.GONE);

                // Cập nhật adapter
                matchAdapter.setMatches(favoriteMatches);

                // Cập nhật trạng thái ngôi sao (ở màn hình này, tất cả đều là yêu thích)
                HashSet<Integer> favoriteIds = favoriteMatches.stream()
                        .map(Match::getMatchId)
                        .collect(Collectors.toCollection(HashSet::new));
                matchAdapter.setFavoriteMatchIds(favoriteIds);
            }
        });
    }
}