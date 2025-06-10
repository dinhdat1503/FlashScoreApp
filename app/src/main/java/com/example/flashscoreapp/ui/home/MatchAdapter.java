package com.example.flashscoreapp.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.model.Score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Match match);
    }

    private List<Match> matches = new ArrayList<>();
    private OnItemClickListener listener; // The listener instance

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        // No change needed here, the ViewHolder will be linked to this adapter instance
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.bind(match);
    }

    @Override
    public int getItemCount() {
        return matches == null ? 0 : matches.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setMatches(List<Match> newMatches) {
        this.matches = newMatches;
        notifyDataSetChanged();
    }

    // The 'static' keyword is removed
    class MatchViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStatus, textViewLeague, textViewHomeTeam, textViewAwayTeam, textViewScore;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            textViewLeague = itemView.findViewById(R.id.text_view_league);
            textViewHomeTeam = itemView.findViewById(R.id.text_view_home_team);
            textViewAwayTeam = itemView.findViewById(R.id.text_view_away_team);
            textViewScore = itemView.findViewById(R.id.text_view_score);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                // This now works because the non-static inner class can access the outer class's 'listener'
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(matches.get(position));
                }
            });
        }

        public void bind(Match match) {
            // ... bind logic remains the same
            textViewStatus.setText(match.getStatus());
            textViewLeague.setText(match.getLeague().getName());
            textViewHomeTeam.setText(match.getHomeTeam().getName());
            textViewAwayTeam.setText(match.getAwayTeam().getName());

            if (match.getStatus().equals("NS")) { // Not Started
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Hiển thị giờ Việt Nam
                textViewScore.setText(sdf.format(new Date(match.getMatchTime())));
            } else {
                Score score = match.getScore();
                String scoreText = score.getHome() + " - " + score.getAway();
                textViewScore.setText(scoreText);
            }
        }
    }
}