package com.example.flashscoreapp.ui.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.ui.details.MatchDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerViewMatches;
    private MatchAdapter matchAdapter;
    private ProgressBar progressBar;
    private TextView textCurrentDate, textPreviousDay, textNextDay, textNoMatches;

    private Calendar currentCalendar = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        textCurrentDate = view.findViewById(R.id.text_current_date);
        textPreviousDay = view.findViewById(R.id.text_previous_day);
        textNextDay = view.findViewById(R.id.text_next_day);
        textNoMatches = view.findViewById(R.id.text_no_matches);

        setupRecyclerView(view);
        setupDateControls(); // Logic mới sẽ nằm trong hàm này

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        observeViewModel();

        updateDateAndFetchMatches();
    }

    private void setupDateControls() {
        // Sự kiện cho nút "Previous Day"
        textPreviousDay.setOnClickListener(v -> {
            currentCalendar.add(Calendar.DAY_OF_YEAR, -1);
            updateDateAndFetchMatches();
        });

        // Sự kiện cho nút "Next Day"
        textNextDay.setOnClickListener(v -> {
            currentCalendar.add(Calendar.DAY_OF_YEAR, 1);
            updateDateAndFetchMatches();
        });


        textCurrentDate.setOnClickListener(v -> {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Cập nhật Calendar với ngày người dùng đã chọn
                    currentCalendar.set(Calendar.YEAR, year);
                    currentCalendar.set(Calendar.MONTH, month);
                    currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Gọi hàm để cập nhật UI và tải dữ liệu, giống hệt như khi nhấn Next/Previous
                    updateDateAndFetchMatches();
                }
            };

            // 2. Lấy ngày, tháng, năm hiện tại từ `currentCalendar` để hiển thị trên Lịch
            int year = currentCalendar.get(Calendar.YEAR);
            int month = currentCalendar.get(Calendar.MONTH);
            int day = currentCalendar.get(Calendar.DAY_OF_MONTH);

            // 3. Tạo và hiển thị DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), dateSetListener, year, month, day);
            datePickerDialog.show();
        });
        // =============================================================
    }

    private void updateDateAndFetchMatches() {
        textCurrentDate.setText(dateFormat.format(currentCalendar.getTime()));

        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateForApi = apiDateFormat.format(currentCalendar.getTime());

        homeViewModel.fetchMatchesForDate(dateForApi);
    }

    private void setupRecyclerView(View view) {
        recyclerViewMatches = view.findViewById(R.id.recycler_view_matches);
        matchAdapter = new MatchAdapter();
        matchAdapter.setOnItemClickListener(match -> {
            Intent intent = new Intent(getActivity(), MatchDetailsActivity.class);
            // Thay vì chỉ gửi ID, chúng ta gửi cả đối tượng Match
            intent.putExtra("EXTRA_MATCH", match);
            startActivity(intent);
        });
        recyclerViewMatches.setAdapter(matchAdapter);
    }

    private void observeViewModel() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewMatches.setVisibility(View.GONE);
        textNoMatches.setVisibility(View.GONE);

        homeViewModel.getMatches().observe(getViewLifecycleOwner(), matches -> {
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
    }
}