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
import com.bumptech.glide.Glide;
import android.widget.ImageView;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Match match);
    }

    private List<Match> matches = new ArrayList<>();
    private OnItemClickListener listener;

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
        private final ImageView imageViewHomeLogo, imageViewAwayLogo;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            textViewLeague = itemView.findViewById(R.id.text_view_league);
            textViewHomeTeam = itemView.findViewById(R.id.text_view_home_team);
            textViewAwayTeam = itemView.findViewById(R.id.text_view_away_team);
            textViewScore = itemView.findViewById(R.id.text_view_score);
            imageViewHomeLogo = itemView.findViewById(R.id.image_view_home_logo);
            imageViewAwayLogo = itemView.findViewById(R.id.image_view_away_logo);
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                // This now works because the non-static inner class can access the outer class's 'listener'
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(matches.get(position));
                }
            });
        }

        public void bind(Match match) {
            textViewStatus.setText(match.getStatus());
            textViewLeague.setText(match.getLeague().getName());
            textViewHomeTeam.setText(match.getHomeTeam().getName());
            textViewAwayTeam.setText(match.getAwayTeam().getName());

            if (match.getStatus().equals("NS")) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Ho_Chi_Minh")); // Hiển thị giờ Việt Nam
                textViewScore.setText(sdf.format(new Date(match.getMatchTime())));
            } else {
                Score score = match.getScore();
                String scoreText = score.getHome() + " - " + score.getAway();
                textViewScore.setText(scoreText);
            }

            Glide.with(itemView.getContext())
                    .load(match.getHomeTeam().getLogoUrl())
                    .placeholder(R.drawable.ic_leagues_24)
                    .error(R.drawable.ic_settings_24)
                    .into(imageViewHomeLogo);

            // Tải logo đội khách
            Glide.with(itemView.getContext())
                    .load(match.getAwayTeam().getLogoUrl())
                    .placeholder(R.drawable.ic_leagues_24)
                    .error(R.drawable.ic_settings_24)
                    .into(imageViewAwayLogo);
        }
    }
}