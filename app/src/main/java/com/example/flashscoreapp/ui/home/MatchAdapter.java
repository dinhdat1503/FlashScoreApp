package com.example.flashscoreapp.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.model.Score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Match match);
        void onFavoriteClick(Match match, boolean isFavorite);
    }

    private List<Match> matches = new ArrayList<>();
    private OnItemClickListener listener;
    private Set<Integer> favoriteMatchIds = new HashSet<>();

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        boolean isFavorite = favoriteMatchIds.contains(match.getMatchId());
        // Lỗi "Expected 1 argument but found 2" được sửa ở đây, vì hàm bind() dưới đây đã được định nghĩa để nhận 2 tham số.
        holder.bind(match, isFavorite);
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

    @SuppressLint("NotifyDataSetChanged")
    public void setFavoriteMatchIds(Set<Integer> newFavoriteMatchIds) {
        this.favoriteMatchIds = newFavoriteMatchIds;
        notifyDataSetChanged();
    }

    class MatchViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStatus, textViewLeague, textViewHomeTeam, textViewAwayTeam, textViewScore;
        private final ImageView imageViewHomeLogo, imageViewAwayLogo;
        // Lỗi "Cannot resolve symbol 'imageViewFavorite'" được sửa ở đây: Khai báo biến.
        private final ImageView imageViewFavorite;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            textViewLeague = itemView.findViewById(R.id.text_view_league);
            textViewHomeTeam = itemView.findViewById(R.id.text_view_home_team);
            textViewAwayTeam = itemView.findViewById(R.id.text_view_away_team);
            textViewScore = itemView.findViewById(R.id.text_view_score);
            imageViewHomeLogo = itemView.findViewById(R.id.image_view_home_logo);
            imageViewAwayLogo = itemView.findViewById(R.id.image_view_away_logo);
            imageViewFavorite = itemView.findViewById(R.id.image_view_favorite);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(matches.get(position));
                }
            });

            imageViewFavorite.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    Match match = matches.get(position);
                    boolean isCurrentlyFavorite = favoriteMatchIds.contains(match.getMatchId());
                    listener.onFavoriteClick(match, isCurrentlyFavorite);
                }
            });
        }

        // Lỗi "Cannot resolve symbol 'isFavorite'" được sửa ở đây: Định nghĩa hàm bind với tham số isFavorite.
        public void bind(Match match, boolean isFavorite) {
            textViewStatus.setText(match.getStatus());
            textViewLeague.setText(match.getLeague().getName());
            textViewHomeTeam.setText(match.getHomeTeam().getName());
            textViewAwayTeam.setText(match.getAwayTeam().getName());

            if ("NS".equals(match.getStatus())) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
                textViewScore.setText(sdf.format(new Date(match.getMatchTime())));
            } else {
                Score score = match.getScore();
                String scoreText = score.getHome() + " - " + score.getAway();
                textViewScore.setText(scoreText);
            }

            Glide.with(itemView.getContext()).load(match.getHomeTeam().getLogoUrl()).placeholder(R.drawable.ic_leagues_24).error(R.drawable.ic_settings_24).into(imageViewHomeLogo);
            Glide.with(itemView.getContext()).load(match.getAwayTeam().getLogoUrl()).placeholder(R.drawable.ic_leagues_24).error(R.drawable.ic_settings_24).into(imageViewAwayLogo);

            if (isFavorite) {
                imageViewFavorite.setImageResource(R.drawable.ic_star_filled);
            } else {
                imageViewFavorite.setImageResource(R.drawable.ic_star_empty);
            }
        }
    }
}