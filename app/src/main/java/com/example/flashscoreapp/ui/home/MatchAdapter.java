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
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.Score;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        holder.bind(match, isFavorite);

        // --- QUAN TRỌNG: XỬ LÝ SỰ KIỆN CLICK Ở ĐÂY ---
        // Vì onBindViewHolder không phải static, nó có thể truy cập 'listener'
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(match);
            }
        });

        holder.imageViewFavorite.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFavoriteClick(match, isFavorite);
            }
        });
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

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewStatus, textViewLeague, textViewHomeTeam, textViewAwayTeam, textViewScore;
        private final ImageView imageViewHomeLogo, imageViewAwayLogo;
        public final ImageView imageViewFavorite;

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

        }

        public void bind(Match match, boolean isFavorite) {
            textViewStatus.setText(match.getStatus());

            if(match.getLeague() != null) textViewLeague.setText(match.getLeague().getName());
            if(match.getHomeTeam() != null) textViewHomeTeam.setText(match.getHomeTeam().getName());
            if(match.getAwayTeam() != null) textViewAwayTeam.setText(match.getAwayTeam().getName());

            if ("NS".equals(match.getStatus())) { // "Not Started"
                // Kiểm tra xem trận đấu có phải là hôm nay không
                Calendar matchCal = Calendar.getInstance();
                matchCal.setTimeInMillis(match.getMatchTime());
                Calendar todayCal = Calendar.getInstance();
                boolean isToday = todayCal.get(Calendar.YEAR) == matchCal.get(Calendar.YEAR) &&
                        todayCal.get(Calendar.DAY_OF_YEAR) == matchCal.get(Calendar.DAY_OF_YEAR);

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeFormat.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
                String timeStr = timeFormat.format(new Date(match.getMatchTime()));

                if (!isToday) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());
                    String dateStr = dateFormat.format(new Date(match.getMatchTime()));
                    textViewScore.setText(dateStr + "\n" + timeStr); // Hiển thị dạng "dd.MM" và giờ ở dòng dưới
                    textViewScore.setTextSize(14f); // Giảm kích thước chữ cho phù hợp
                } else {
                    textViewScore.setText(timeStr); // Nếu là hôm nay, chỉ hiển thị giờ
                    textViewScore.setTextSize(20f);
                }
            } else {
                Score score = match.getScore();
                if(score != null) {
                    String scoreText = score.getHome() + " - " + score.getAway();
                    textViewScore.setText(scoreText);
                    textViewScore.setTextSize(20f);
                }
            }

            if(match.getHomeTeam() != null) Glide.with(itemView.getContext()).load(match.getHomeTeam().getLogoUrl()).placeholder(R.drawable.ic_leagues_24).error(R.drawable.ic_settings_24).into(imageViewHomeLogo);
            if(match.getAwayTeam() != null) Glide.with(itemView.getContext()).load(match.getAwayTeam().getLogoUrl()).placeholder(R.drawable.ic_leagues_24).error(R.drawable.ic_settings_24).into(imageViewAwayLogo);

            if (isFavorite) {
                imageViewFavorite.setImageResource(R.drawable.ic_star_filled);
            } else {
                imageViewFavorite.setImageResource(R.drawable.ic_star_empty);
            }
        }
    }
}