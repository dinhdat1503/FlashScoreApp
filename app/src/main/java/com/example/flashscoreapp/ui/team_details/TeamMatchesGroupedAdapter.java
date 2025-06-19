package com.example.flashscoreapp.ui.team_details;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.League;
import com.example.flashscoreapp.data.model.domain.Match;
import com.example.flashscoreapp.data.model.domain.Score;
import com.example.flashscoreapp.ui.match_details.MatchDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TeamMatchesGroupedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_LEAGUE_HEADER = 0;
    private static final int TYPE_MATCH = 1;

    private List<Object> displayList = new ArrayList<>();
    private final int teamId;

    public TeamMatchesGroupedAdapter(int teamId) {
        this.teamId = teamId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDisplayList(List<Object> displayList) {
        this.displayList = displayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (displayList.get(position) instanceof League) {
            return TYPE_LEAGUE_HEADER;
        }
        return TYPE_MATCH;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_LEAGUE_HEADER) {
            View view = inflater.inflate(R.layout.item_league_header, parent, false);
            // ⭐ SỬA LỖI: Sử dụng LeagueHeaderViewHolder của chính Adapter này
            return new LeagueHeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_match_result, parent, false);
            return new MatchViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_LEAGUE_HEADER) {
            // ⭐ SỬA LỖI: Ép kiểu về ViewHolder của chính Adapter này
            ((LeagueHeaderViewHolder) holder).bind((League) displayList.get(position));
        } else {
            ((MatchViewHolder) holder).bind((Match) displayList.get(position), teamId);
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    // ⭐ GIẢI PHÁP: Sao chép LeagueHeaderViewHolder vào đây để Adapter tự quản lý
    static class LeagueHeaderViewHolder extends RecyclerView.ViewHolder {
        private final ImageView leagueLogo;
        private final TextView leagueName, leagueCountry;

        public LeagueHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueLogo = itemView.findViewById(R.id.image_header_league_logo);
            leagueName = itemView.findViewById(R.id.text_header_league_name);
            leagueCountry = itemView.findViewById(R.id.text_header_league_country);
        }

        void bind(League league) {
            leagueName.setText(league.getName());
            leagueCountry.setText(league.getCountry());
            Glide.with(itemView.getContext())
                    .load(league.getLogoUrl())
                    .placeholder(R.drawable.ic_leagues_24)
                    .into(leagueLogo);
        }
    }

    // ViewHolder cho trận đấu
    static class MatchViewHolder extends RecyclerView.ViewHolder {
        private final TextView textDate, textHomeTeam, textAwayTeam, textHomeScore, textAwayScore, textResult;
        private final ImageView imageHomeLogo, imageAwayLogo;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.text_match_date);
            textHomeTeam = itemView.findViewById(R.id.text_home_team_name);
            textAwayTeam = itemView.findViewById(R.id.text_away_team_name);
            textHomeScore = itemView.findViewById(R.id.text_home_score);
            textAwayScore = itemView.findViewById(R.id.text_away_score);
            textResult = itemView.findViewById(R.id.text_match_result);
            imageHomeLogo = itemView.findViewById(R.id.image_home_logo);
            imageAwayLogo = itemView.findViewById(R.id.image_away_logo);
        }

        void bind(Match match, int teamId) {
            Context context = itemView.getContext();

            // Hiển thị ngày
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.", Locale.getDefault());
            textDate.setText(dateFormat.format(new Date(match.getMatchTime())));

            // Tên và logo
            textHomeTeam.setText(match.getHomeTeam().getName());
            textAwayTeam.setText(match.getAwayTeam().getName());
            Glide.with(context).load(match.getHomeTeam().getLogoUrl()).into(imageHomeLogo);
            Glide.with(context).load(match.getAwayTeam().getLogoUrl()).into(imageAwayLogo);

            // Tỷ số và kết quả
            if ("FT".equals(match.getStatus())) {
                Score score = match.getScore();
                textHomeScore.setText(String.valueOf(score.getHome()));
                textAwayScore.setText(String.valueOf(score.getAway()));
                textResult.setVisibility(View.VISIBLE);

                int homeScore = score.getHome();
                int awayScore = score.getAway();
                int resultColor;
                String resultText;

                if (homeScore == awayScore) {
                    resultText = "H";
                    resultColor = ContextCompat.getColor(context, android.R.color.darker_gray);
                } else if ((match.getHomeTeam().getId() == teamId && homeScore > awayScore) ||
                        (match.getAwayTeam().getId() == teamId && awayScore > homeScore)) {
                    resultText = "T";
                    resultColor = ContextCompat.getColor(context, R.color.green);
                } else {
                    resultText = "B";
                    resultColor = ContextCompat.getColor(context, R.color.selected_date_color);
                }
                textResult.setText(resultText);
                ((GradientDrawable) textResult.getBackground()).setColor(resultColor);

            } else { // Trận đấu chưa diễn ra
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                textHomeScore.setText("-");
                textAwayScore.setText(timeFormat.format(new Date(match.getMatchTime())));
                textResult.setVisibility(View.GONE);
            }

            // Click listener để vào chi tiết trận đấu
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MatchDetailsActivity.class);
                intent.putExtra("EXTRA_MATCH", match);
                context.startActivity(intent);
            });
        }
    }
}