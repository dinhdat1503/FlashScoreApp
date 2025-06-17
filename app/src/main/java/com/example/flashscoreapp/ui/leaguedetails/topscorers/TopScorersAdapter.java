package com.example.flashscoreapp.ui.leaguedetails.topscorers;

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
import com.example.flashscoreapp.data.model.remote.ApiStatisticsData;
import com.example.flashscoreapp.data.model.remote.ApiTopScorerData;
import java.util.ArrayList;
import java.util.List;

public class TopScorersAdapter extends RecyclerView.Adapter<TopScorersAdapter.TopScorerViewHolder> {

    private List<ApiTopScorerData> topScorers = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setTopScorers(List<ApiTopScorerData> scorers) {
        this.topScorers = scorers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopScorerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_scorer, parent, false);
        return new TopScorerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopScorerViewHolder holder, int position) {
        ApiTopScorerData scorerData = topScorers.get(position);
        holder.bind(scorerData, position + 1);
    }

    @Override
    public int getItemCount() {
        return topScorers != null ? topScorers.size() : 0;
    }

    public static class TopScorerViewHolder extends RecyclerView.ViewHolder {
        private final TextView textRank, textPlayerName, textPlayerTeamName, textGoals, textAssists;
        private final ImageView imagePlayerPhoto;

        public TopScorerViewHolder(@NonNull View itemView) {
            super(itemView);
            textRank = itemView.findViewById(R.id.text_player_rank);
            imagePlayerPhoto = itemView.findViewById(R.id.image_player_photo);
            textPlayerName = itemView.findViewById(R.id.text_player_name);
            textPlayerTeamName = itemView.findViewById(R.id.text_player_team_name);
            textGoals = itemView.findViewById(R.id.text_goals);
            textAssists = itemView.findViewById(R.id.text_assists);
        }

        public void bind(ApiTopScorerData scorerData, int rank) {
            textRank.setText(String.valueOf(rank));

            if (scorerData.getPlayer() != null) {
                textPlayerName.setText(scorerData.getPlayer().getName());
                Glide.with(itemView.getContext())
                        .load(scorerData.getPlayer().getPhoto())
                        .placeholder(R.drawable.ic_settings_24) // Ảnh mặc định
                        .error(R.drawable.ic_settings_24)      // Ảnh khi có lỗi
                        .circleCrop() // Bo tròn ảnh
                        .into(imagePlayerPhoto);
            }

            if (scorerData.getStatistics() != null && !scorerData.getStatistics().isEmpty()) {
                ApiStatisticsData stats = scorerData.getStatistics().get(0);

                if (stats.getTeam() != null) {
                    textPlayerTeamName.setText(stats.getTeam().getName());
                }

                if (stats.getGoals() != null) {
                    textGoals.setText(stats.getGoals().getTotal() != null ? String.valueOf(stats.getGoals().getTotal()) : "-");
                    textAssists.setText(stats.getGoals().getAssists() != null ? String.valueOf(stats.getGoals().getAssists()) : "-");
                }
            }
        }
    }
}