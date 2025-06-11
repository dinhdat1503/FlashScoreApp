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
import com.example.flashscoreapp.data.model.League;
import java.util.ArrayList;
import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private List<League> leagues = new ArrayList<>();
    public interface OnLeagueClickListener {
        void onLeagueClick(League league);
    }
    private OnLeagueClickListener clickListener;
    public void setOnLeagueClickListener(OnLeagueClickListener listener) {
        this.clickListener = listener;
    }
    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league, parent, false);
        return new LeagueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder holder, int position) {
        holder.bind(leagues.get(position));
    }

    @Override
    public int getItemCount() {
        return leagues.size();
    }

    class LeagueViewHolder extends RecyclerView.ViewHolder {
        private ImageView leagueLogo;
        private TextView leagueName;

        public LeagueViewHolder(@NonNull View itemView) {
            super(itemView);
            leagueLogo = itemView.findViewById(R.id.image_league_logo);
            leagueName = itemView.findViewById(R.id.text_league_name);
            itemView.setOnClickListener(v -> {
                if (clickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    clickListener.onLeagueClick(leagues.get(getAdapterPosition()));
                }
            });
        }

        void bind(League league) {
            leagueName.setText(league.getName());
            Glide.with(itemView.getContext())
                    .load(league.getLogoUrl())
                    .placeholder(R.drawable.ic_leagues_24)
                    .into(leagueLogo);
        }
    }
}