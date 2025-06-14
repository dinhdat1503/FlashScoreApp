package com.example.flashscoreapp.ui.home;

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
import com.example.flashscoreapp.ui.StandingsAdapter;
import com.example.flashscoreapp.ui.StandingsViewModel;
import com.example.flashscoreapp.ui.StandingsViewModelFactory;

import java.util.ArrayList;
import java.util.Calendar;

public class StandingsTabFragment extends Fragment {

    private StandingsViewModel standingsViewModel;
    private RecyclerView recyclerView;
    private StandingsAdapter adapter;
    private int leagueId;

    public static StandingsTabFragment newInstance(int leagueId, int season) {
        StandingsTabFragment fragment = new StandingsTabFragment();
        Bundle args = new Bundle();
        args.putInt("LEAGUE_ID", leagueId);
        args.putInt("SEASON", season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Lấy và lưu trữ leagueId từ arguments khi Fragment được tạo
        if (getArguments() != null) {
            this.leagueId = getArguments().getInt("LEAGUE_ID");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Sử dụng layout mới đã tạo ở bước trước
        return inflater.inflate(R.layout.fragment_tab_standings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_standings);
        adapter = new StandingsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Giờ đây, biến `leagueId` đã được khởi tạo và có thể sử dụng an toàn
        StandingsViewModelFactory factory = new StandingsViewModelFactory(getActivity().getApplication(), leagueId);
        standingsViewModel = new ViewModelProvider(this, factory).get(StandingsViewModel.class);

        // Lấy mùa giải từ arguments để tải dữ liệu
        int season = getArguments().getInt("SEASON", Calendar.getInstance().get(Calendar.YEAR) - 1);
        standingsViewModel.loadStandingsForSeason(season);

        observeViewModel();
    }

    private void observeViewModel() {
        standingsViewModel.getStandings().observe(getViewLifecycleOwner(), standings -> {
            if (standings != null) {
                adapter.setStandings(standings);
            } else {
                adapter.setStandings(new ArrayList<>());
            }
        });
    }
}