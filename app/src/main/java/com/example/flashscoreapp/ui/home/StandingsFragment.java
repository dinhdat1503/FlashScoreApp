package com.example.flashscoreapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.ui.StandingsAdapter;
import com.example.flashscoreapp.ui.StandingsViewModel;
import com.example.flashscoreapp.ui.StandingsViewModelFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StandingsFragment extends Fragment {

    private StandingsViewModel standingsViewModel;
    private RecyclerView recyclerView;
    private StandingsAdapter adapter;
    private Spinner seasonSpinner;
    private int leagueId;
    private String leagueName;

    public static StandingsFragment newInstance(int leagueId, String leagueName) {
        StandingsFragment fragment = new StandingsFragment();
        Bundle args = new Bundle();
        args.putInt("LEAGUE_ID", leagueId);
        args.putString("LEAGUE_NAME", leagueName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            leagueId = getArguments().getInt("LEAGUE_ID");
            leagueName = getArguments().getString("LEAGUE_NAME");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_standings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(leagueName);
        }

        recyclerView = view.findViewById(R.id.recycler_view_standings);
        seasonSpinner = view.findViewById(R.id.spinner_season);
        adapter = new StandingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Khởi tạo ViewModel bằng Factory mới
        StandingsViewModelFactory factory = new StandingsViewModelFactory(getActivity().getApplication(), leagueId);
        standingsViewModel = new ViewModelProvider(this, factory).get(StandingsViewModel.class);

        setupSeasonSpinner();
        observeViewModel();
    }

    private void setupSeasonSpinner() {
        // Tạo danh sách các mùa giải (ví dụ: từ mùa vừa kết thúc về 10 mùa trước)
        List<String> seasons = new ArrayList<>();
        // Lấy năm hiện tại
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Bắt đầu từ mùa giải gần nhất (ví dụ: nếu bây giờ là 2025, ta bắt đầu từ mùa 2024-2025)
        for (int i = 0; i < 10; i++) {
            int seasonStartYear = currentYear - 1 - i;
            String seasonDisplay = seasonStartYear + "-" + (seasonStartYear + 1);
            seasons.add(seasonDisplay);
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, seasons);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(spinnerAdapter);

        // Đặt listener cho Spinner
        seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSeasonDisplay = (String) parent.getItemAtPosition(position);
                // Lấy ra năm bắt đầu từ chuỗi "YYYY-YYYY" (ví dụ: "2023-2024" -> 2023)
                int selectedSeasonApi = Integer.parseInt(selectedSeasonDisplay.substring(0, 4));

                // Khi người dùng chọn một mùa giải, gọi ViewModel để tải dữ liệu
                standingsViewModel.loadStandingsForSeason(selectedSeasonApi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void observeViewModel() {
        standingsViewModel.getStandings().observe(getViewLifecycleOwner(), standings -> {
            if (standings != null) {
                adapter.setStandings(standings);
            } else {
                adapter.setStandings(new ArrayList<>()); // Xóa list cũ nếu có lỗi
            }
        });
    }
}