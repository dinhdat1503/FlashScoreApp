package com.example.flashscoreapp.ui.match_details.statistics;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.MatchStatistic;
import java.util.ArrayList;
import java.util.List;

public class MatchStatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_STAT = 1;

    private List<Object> displayList = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setDisplayList(List<Object> displayList) {
        this.displayList = displayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (displayList.get(position) instanceof StatisticHeader) {
            return TYPE_HEADER;
        }
        return TYPE_STAT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_statistic_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_statistic_summary, parent, false);
            return new StatisticViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            ((HeaderViewHolder) holder).bind((StatisticHeader) displayList.get(position));
        } else {
            ((StatisticViewHolder) holder).bind((MatchStatistic) displayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return displayList != null ? displayList.size() : 0;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView headerTitle;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.text_statistic_header);
        }
        void bind(StatisticHeader header) {
            headerTitle.setText(header.getTitle());
        }
    }

    static class StatisticViewHolder extends RecyclerView.ViewHolder {
        private final TextView homeValue, awayValue, type;
        private final View barHome, barAway;

        public StatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            homeValue = itemView.findViewById(R.id.text_stat_home_value);
            awayValue = itemView.findViewById(R.id.text_stat_away_value);
            type = itemView.findViewById(R.id.text_stat_type);
            barHome = itemView.findViewById(R.id.bar_home);
            barAway = itemView.findViewById(R.id.bar_away);
        }

        void bind(MatchStatistic stat) {
            homeValue.setText(stat.getHomeValue());
            awayValue.setText(stat.getAwayValue());
            type.setText(stat.getType());

            try {
                float homeStat = Float.parseFloat(stat.getHomeValue().replaceAll("[^0-9.]", ""));
                float awayStat = Float.parseFloat(stat.getAwayValue().replaceAll("[^0-9.]", ""));
                float total = homeStat + awayStat;

                LinearLayout.LayoutParams homeParams = (LinearLayout.LayoutParams) barHome.getLayoutParams();
                LinearLayout.LayoutParams awayParams = (LinearLayout.LayoutParams) barAway.getLayoutParams();

                if (total > 0) {
                    homeParams.weight = homeStat;
                    awayParams.weight = awayStat;
                } else {
                    homeParams.weight = 0;
                    awayParams.weight = 0;
                }
                barHome.setLayoutParams(homeParams);
                barAway.setLayoutParams(awayParams);

            } catch (NumberFormatException | NullPointerException e) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) barHome.getLayoutParams();
                params.weight = 0;
                barHome.setLayoutParams(params);
                barAway.setLayoutParams(params);
            }
        }
    }
}