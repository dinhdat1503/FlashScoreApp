package com.example.flashscoreapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.StandingItem;
import java.util.ArrayList;
import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.StandingViewHolder> {

    private List<StandingItem> standings = new ArrayList<>();

    public void setStandings(List<StandingItem> standings) {
        this.standings = standings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StandingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_standing, parent, false);
        return new StandingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StandingViewHolder holder, int position) {
        holder.bind(standings.get(position));
    }

    @Override
    public int getItemCount() {
        return standings.size();
    }

    class StandingViewHolder extends RecyclerView.ViewHolder {
        TextView textRank, textTeamName, textPlayed, textWin, textDraw, textLose, textGoalsDiff, textPoints;
        ImageView imageTeamLogo;

        public StandingViewHolder(@NonNull View itemView) {
            super(itemView);
            textRank = itemView.findViewById(R.id.text_rank);
            textTeamName = itemView.findViewById(R.id.text_team_name);
            textPlayed = itemView.findViewById(R.id.text_played);
            textWin = itemView.findViewById(R.id.text_win);
            textDraw = itemView.findViewById(R.id.text_draw);
            textLose = itemView.findViewById(R.id.text_lose);
            textGoalsDiff = itemView.findViewById(R.id.text_goals_diff);
            textPoints = itemView.findViewById(R.id.text_points);
            imageTeamLogo = itemView.findViewById(R.id.image_team_logo);
        }

        void bind(StandingItem item) {
            textRank.setText(String.valueOf(item.getRank()));
            textTeamName.setText(item.getTeam().getName());
            textPlayed.setText(String.valueOf(item.getAll().getPlayed()));
            textWin.setText(String.valueOf(item.getAll().getWin()));
            textDraw.setText(String.valueOf(item.getAll().getDraw()));
            textLose.setText(String.valueOf(item.getAll().getLose()));
            textGoalsDiff.setText(String.valueOf(item.getGoalsDiff()));
            textPoints.setText(String.valueOf(item.getPoints()));
            Glide.with(itemView.getContext()).load(item.getTeam().getLogo()).into(imageTeamLogo);
        }
    }
}