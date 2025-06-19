package com.example.flashscoreapp.ui.team_details.squad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashscoreapp.R;
import com.example.flashscoreapp.ui.team_details.TeamDetailsViewModel;

/**
 * Fragment chịu trách nhiệm hiển thị danh sách đội hình (squad).
 */
public final class TeamSquadFragment extends Fragment {

    private TeamDetailsViewModel viewModel;
    private SquadAdapter squadAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_team_squad, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_squad);
        progressBar = view.findViewById(R.id.progress_bar_squad);
        emptyTextView = view.findViewById(R.id.text_view_empty);

        setupRecyclerView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lấy ViewModel được chia sẻ từ Activity chứa Fragment này.
        // Điều này đảm bảo ViewModel tồn tại xuyên suốt các tab.
        viewModel = new ViewModelProvider(requireActivity()).get(TeamDetailsViewModel.class);

        observeSquadData();
    }

    private void setupRecyclerView() {
        squadAdapter = new SquadAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(squadAdapter);
    }

    private void observeSquadData() {
        setLoadingState(true);

        viewModel.getSquad().observe(getViewLifecycleOwner(), players -> {
            setLoadingState(false);
            if (players != null && !players.isEmpty()) {
                recyclerView.setVisibility(View.VISIBLE);
                squadAdapter.submitList(players);
            } else {
                emptyTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setLoadingState(final boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}