package com.example.flashscoreapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.ui.LeagueAdapter;
import com.example.flashscoreapp.ui.LeagueDetailsFragment;
import com.example.flashscoreapp.ui.LeaguesViewModel;
import com.example.flashscoreapp.data.model.ApiLeagueData;
import com.example.flashscoreapp.data.model.League;
import com.example.flashscoreapp.data.model.Season;

import java.util.List;

public class LeaguesFragment extends Fragment {

    private LeaguesViewModel leaguesViewModel;
    private RecyclerView recyclerView;
    private LeagueAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_leagues, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar_leagues);
        recyclerView = view.findViewById(R.id.recycler_view_leagues);
        adapter = new LeagueAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);


        adapter.setOnLeagueClickListener(leagueData -> {
            League league = leagueData.getLeague();
            List<Season> seasons = leagueData.getSeasons();

            if (seasons != null && !seasons.isEmpty()) {
                // Truyền đi cả danh sách seasons
                LeagueDetailsFragment detailsFragment = LeagueDetailsFragment.newInstance(league.getId(), league.getName(), league.getLogoUrl(), seasons);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailsFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Không tìm thấy thông tin mùa giải", Toast.LENGTH_SHORT).show();
            }
        });

        leaguesViewModel = new ViewModelProvider(this).get(LeaguesViewModel.class);
        observeViewModel();
    }

    private void observeViewModel() {
        progressBar.setVisibility(View.VISIBLE);
        leaguesViewModel.getLeaguesData().observe(getViewLifecycleOwner(), leaguesData -> {
            progressBar.setVisibility(View.GONE);
            if (leaguesData != null) {
                adapter.setLeaguesData(leaguesData);
            }
        });
    }
}