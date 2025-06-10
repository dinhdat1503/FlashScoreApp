package com.example.flashscoreapp.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.model.MatchStatistic;

public class MatchDetailsActivity extends AppCompatActivity {

    private MatchDetailsViewModel viewModel;
    private Match match;

    // Khai báo các View
    private TextView textViewMatchTitle;
    private TextView textViewStatistics;
    private RecyclerView recyclerEvents;
    private MatchEventAdapter eventAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_details);


        textViewMatchTitle = findViewById(R.id.text_view_match_title);
        textViewStatistics = findViewById(R.id.text_view_statistics);
        recyclerEvents = findViewById(R.id.recycler_view_events);
        // ------------------------------------

        // Lấy đối tượng Match từ Intent
        Intent intent = getIntent();
        match = (Match) intent.getSerializableExtra("EXTRA_MATCH");

        if (match == null) {
            Toast.makeText(this, "Lỗi: không tìm thấy dữ liệu trận đấu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cập nhật tiêu đề ngay sau khi có dữ liệu `match`
        updateMatchHeader();

        // Thiết lập RecyclerView
        setupRecyclerView();

        // Khởi tạo ViewModel
        MatchDetailsViewModelFactory factory = new MatchDetailsViewModelFactory(getApplication(), match.getMatchId());
        viewModel = new ViewModelProvider(this, factory).get(MatchDetailsViewModel.class);

        // Lắng nghe dữ liệu từ ViewModel
        observeViewModel();
    }

    private void setupRecyclerView() {
        eventAdapter = new MatchEventAdapter();
        recyclerEvents.setAdapter(eventAdapter);
        // Bạn có thể thêm các tuỳ chỉnh khác cho RecyclerView ở đây
    }

    private void updateMatchHeader() {
        if (match == null) return;

        String title;
        if (match.getStatus().equals("NS")) {
            title = match.getHomeTeam().getName() + " vs " + match.getAwayTeam().getName();
        } else {
            title = match.getHomeTeam().getName() + " " +
                    match.getScore().getHome() + " - " + match.getScore().getAway() + " " +
                    match.getAwayTeam().getName();
        }
        textViewMatchTitle.setText(title);
    }

    private void observeViewModel() {
        viewModel.getMatchDetails().observe(this, details -> {
            if (details != null) {
                // --- CẬP NHẬT CÁCH HIỂN THỊ THỐNG KÊ ---
                StringBuilder statsBuilder = new StringBuilder();
                if (details.getStatistics() != null && !details.getStatistics().isEmpty()) {
                    for (MatchStatistic stat : details.getStatistics()) {
                        statsBuilder
                                .append(stat.getType())
                                .append(":  ") // Thêm khoảng trắng
                                .append(stat.getHomeValue())
                                .append(" - ")
                                .append(stat.getAwayValue())
                                .append("\n\n"); // Thêm 2 lần xuống dòng cho thoáng
                    }
                    textViewStatistics.setText(statsBuilder.toString());
                } else {
                    textViewStatistics.setText("Không có dữ liệu thống kê.");
                }

                // Cập nhật danh sách sự kiện (giữ nguyên)
                if (details.getEvents() != null) {
                    eventAdapter.setEvents(details.getEvents());
                }
            } else {
                Toast.makeText(this, "Không tìm thấy chi tiết cho trận đấu này", Toast.LENGTH_SHORT).show();
            }
        });
    }
}