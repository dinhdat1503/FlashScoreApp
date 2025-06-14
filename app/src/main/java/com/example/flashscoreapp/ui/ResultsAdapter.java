package com.example.flashscoreapp.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.model.RoundHeader;
import com.example.flashscoreapp.ui.home.MatchAdapter;

import java.util.ArrayList;
import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MATCH = 1;

    private List<Object> items = new ArrayList<>();

    public interface OnMatchClickListener {
        void onMatchClick(Match match);
    }

    private OnMatchClickListener matchClickListener;

    public void setOnMatchClickListener(OnMatchClickListener listener) {
        this.matchClickListener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<Object> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof RoundHeader) {
            return TYPE_HEADER;
        } else {
            return TYPE_MATCH;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_round_header, parent, false);
            return new HeaderViewHolder(view);
        } else { // TYPE_MATCH
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
            // Tái sử dụng ViewHolder từ MatchAdapter
            return new MatchAdapter.MatchViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.bind((RoundHeader) items.get(position));
        } else {
            MatchAdapter.MatchViewHolder matchViewHolder = (MatchAdapter.MatchViewHolder) holder;
            Match match = (Match) items.get(position);
            matchViewHolder.bind(match, false);

            // --- GÁN SỰ KIỆN CLICK Ở ĐÂY ---
            holder.itemView.setOnClickListener(v -> {
                if (matchClickListener != null) {
                    matchClickListener.onMatchClick(match);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // ViewHolder cho Header
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView textRoundName;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textRoundName = itemView.findViewById(R.id.text_round_name);
        }
        public void bind(RoundHeader header) {
            textRoundName.setText(header.getRoundName());
        }
    }
}