package com.example.flashscoreapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.ui.match_details.MatchDetailsActivity;
import com.example.flashscoreapp.data.model.domain.Match;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewMatches;
    private RecyclerView recyclerViewDates;
    private MatchAdapter matchAdapter;
    private DateAdapter dateAdapter;
    private ProgressBar progressBar;
    private TextView textNoMatches;

    private final SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        textNoMatches = view.findViewById(R.id.text_no_matches);
        recyclerViewDates = view.findViewById(R.id.recycler_view_dates);

        setupRecyclerView(view); // Hàm này giờ chỉ setup cho RecyclerView trận đấu
        setupDateRecyclerView(); // Hàm mới để setup RecyclerView ngày

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        observeViewModel();

        // Tải trận đấu cho ngày hôm nay khi khởi tạo
        homeViewModel.fetchMatchesForDate(apiDateFormat.format(Calendar.getInstance().getTime()));
    }

    private void setupDateRecyclerView() {
        List<Calendar> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -30); // Bắt đầu từ 30 ngày trước

        for (int i = 0; i < 60; i++) {
            dates.add((Calendar) calendar.clone());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        int todayPosition = 30; // Vị trí của ngày hôm nay trong danh sách

        dateAdapter = new DateAdapter(dates, todayPosition, selectedDate -> {
            String dateForApi = apiDateFormat.format(selectedDate.getTime());
            homeViewModel.fetchMatchesForDate(dateForApi);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDates.setLayoutManager(layoutManager);
        recyclerViewDates.setAdapter(dateAdapter);

        recyclerViewDates.post(() -> {
            // Đầu tiên, cuộn đến vị trí "Hôm nay" để đảm bảo nó nằm trong vùng hiển thị.
            layoutManager.scrollToPosition(todayPosition);

            // Post thêm một tác vụ nữa để chạy sau khi việc cuộn ở trên hoàn tất.
            // Điều này đảm bảo rằng view của "Hôm nay" đã được tạo.
            recyclerViewDates.post(() -> {
                View view = layoutManager.findViewByPosition(todayPosition);
                if (view != null) {
                    // Bây giờ, ta có thể lấy chiều rộng của view và tính toán offset để căn giữa.
                    int offset = recyclerViewDates.getWidth() / 2 - view.getWidth() / 2;
                    layoutManager.scrollToPositionWithOffset(todayPosition, offset);
                }
            });
        });
    }

    private void setupRecyclerView(View view) {
        recyclerViewMatches = view.findViewById(R.id.recycler_view_matches);
        matchAdapter = new MatchAdapter();

        matchAdapter.setOnItemClickListener(new MatchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Match match) {
                Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
                intent.putExtra("EXTRA_MATCH", match);
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(Match match, boolean isFavorite) {
                if (isFavorite) {
                    homeViewModel.removeFavorite(match);
                } else {
                    homeViewModel.addFavorite(match);
                }
            }
        });

        recyclerViewMatches.setAdapter(matchAdapter);
    }

    private void observeViewModel() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewMatches.setVisibility(View.GONE);
        textNoMatches.setVisibility(View.GONE);

        homeViewModel.getMatches().observe(getViewLifecycleOwner(), matches -> {
            // --- THÊM LOG TẠI ĐÂY ---
            Log.d("FragmentLog", "Observer in Fragment triggered. Found " + (matches != null ? matches.size() : "null") + " matches.");

            progressBar.setVisibility(View.GONE);
            if (matches != null && !matches.isEmpty()) {
                recyclerViewMatches.setVisibility(View.VISIBLE);
                textNoMatches.setVisibility(View.GONE);
                matchAdapter.setMatches(matches);
            } else {
                recyclerViewMatches.setVisibility(View.GONE);
                textNoMatches.setVisibility(View.VISIBLE);
            }
        });

        homeViewModel.getFavoriteMatches().observe(getViewLifecycleOwner(), favoriteMatches -> {
            if (favoriteMatches != null) {
                Set<Integer> favoriteIds = favoriteMatches.stream()
                        .map(Match::getMatchId)
                        .collect(Collectors.toSet());
                matchAdapter.setFavoriteMatchIds(favoriteIds);
            }
        });
    }
}