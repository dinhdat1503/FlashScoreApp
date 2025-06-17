package com.example.flashscoreapp.ui.match_details.summary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.MatchStatistic;
import com.example.flashscoreapp.ui.match_details.MatchDetailsActivity;
import com.example.flashscoreapp.ui.match_details.MatchDetailsViewModel;

import java.util.Arrays;
import java.util.List;

public class MatchSummaryFragment extends Fragment {
    private MatchDetailsViewModel viewModel;
    private MatchEventAdapter eventAdapter;
    private LinearLayout statisticsContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_match_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ các View
        statisticsContainer = view.findViewById(R.id.layout_statistics_container);
        RecyclerView recyclerViewEvents = view.findViewById(R.id.recycler_view_summary);

        // Setup RecyclerView cho diễn biến
        eventAdapter = new MatchEventAdapter();
        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewEvents.setAdapter(eventAdapter);

        // Lấy ViewModel từ Activity để chia sẻ dữ liệu
        viewModel = new ViewModelProvider(requireActivity()).get(MatchDetailsViewModel.class);

        // Lắng nghe dữ liệu
        viewModel.getMatchDetails().observe(getViewLifecycleOwner(), details -> {
            if (details != null) {
                // Hiển thị phần thống kê tóm tắt
                if (details.getStatistics() != null) {
                    displayStatisticsSummary(details.getStatistics());
                }

                // Hiển thị danh sách diễn biến trận đấu
                if (details.getEvents() != null) {
                    Match currentMatch = ((MatchDetailsActivity) requireActivity()).getMatch();
                    int homeTeamId = currentMatch.getHomeTeam().getId();
                    eventAdapter.setData(details.getEvents(), homeTeamId);
                }
            }
        });
    }

    private void displayStatisticsSummary(List<MatchStatistic> stats) {
        statisticsContainer.removeAllViews(); // Xóa các view cũ để tránh trùng lặp
        LayoutInflater inflater = LayoutInflater.from(getContext());

        // Chọn ra 3 chỉ số quan trọng để hiển thị tóm tắt
        List<String> keyStats = Arrays.asList("Ball Possession", "Total Shots", "Shots on Goal");

        for (MatchStatistic stat : stats) {
            // Chỉ xử lý các chỉ số nằm trong danh sách keyStats
            if (keyStats.contains(stat.getType())) {
                View statView = inflater.inflate(R.layout.item_statistic_summary, statisticsContainer, false);

                TextView homeValue = statView.findViewById(R.id.text_stat_home_value);
                TextView awayValue = statView.findViewById(R.id.text_stat_away_value);
                TextView type = statView.findViewById(R.id.text_stat_type);
                View barHome = statView.findViewById(R.id.bar_home);
                View barAway = statView.findViewById(R.id.bar_away);

                homeValue.setText(stat.getHomeValue());
                awayValue.setText(stat.getAwayValue());
                type.setText(stat.getType());

                // Tính toán trọng số (weight) cho thanh bar so sánh
                try {
                    // Loại bỏ các ký tự không phải số (như '%')
                    float homeStat = Float.parseFloat(stat.getHomeValue().replaceAll("[^0-9.]", ""));
                    float awayStat = Float.parseFloat(stat.getAwayValue().replaceAll("[^0-9.]", ""));
                    float total = homeStat + awayStat;

                    if (total > 0) {
                        barHome.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, homeStat));
                        barAway.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, awayStat));
                    }
                } catch (NumberFormatException e) {
                    // Nếu dữ liệu không phải là số, ẩn thanh bar đi
                    barHome.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0));
                    barAway.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0));
                }

                statisticsContainer.addView(statView);
            }
        }
    }
}