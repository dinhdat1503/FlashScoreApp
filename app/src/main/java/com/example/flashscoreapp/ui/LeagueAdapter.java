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
import com.example.flashscoreapp.data.model.ApiLeagueData;
import com.example.flashscoreapp.data.model.League;
import java.util.ArrayList;
import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {

    private List<ApiLeagueData> leaguesData = new ArrayList<>();
    public interface OnLeagueClickListener {
        void onLeagueClick(ApiLeagueData leagueData);
    }
    private OnLeagueClickListener clickListener;
    public void setOnLeagueClickListener(OnLeagueClickListener listener) {
        this.clickListener = listener;
    }


    public void setLeaguesData(List<ApiLeagueData> leaguesData) {
        this.leaguesData = leaguesData;
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
        holder.bind(leaguesData.get(position).getLeague());
    }

    @Override
    public int getItemCount() {
        return leaguesData.size();
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
                    // TRUYỀN ĐI NGUYÊN ĐỐI TƯỢNG ApiLeagueData
                    clickListener.onLeagueClick(leaguesData.get(getAdapterPosition()));
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