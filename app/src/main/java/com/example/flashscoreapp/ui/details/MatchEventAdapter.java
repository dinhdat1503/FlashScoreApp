package com.example.flashscoreapp.ui.details;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.MatchEvent;
import java.util.ArrayList;
import java.util.List;

public class MatchEventAdapter extends RecyclerView.Adapter<MatchEventAdapter.EventViewHolder> {

    private List<MatchEvent> events = new ArrayList<>();

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEvents(List<MatchEvent> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private TextView textMinute, textPlayer;
        private ImageView imageIcon;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textMinute = itemView.findViewById(R.id.text_event_minute);
            textPlayer = itemView.findViewById(R.id.text_event_player);
            imageIcon = itemView.findViewById(R.id.image_event_icon);
        }

        public void bind(MatchEvent event) {
            String minuteText = event.getMinute() + "'";
            textMinute.setText(minuteText);

            String playerText = event.getPlayer() + " (" + event.getType() + ")";
            textPlayer.setText(playerText);

            // TODO: Thiết lập icon dựa trên loại sự kiện
            // if (event.getType().equals("Bàn thắng")) {
            //     imageIcon.setImageResource(R.drawable.ic_goal);
            // } else if (event.getType().equals("Thẻ vàng")) {
            //     imageIcon.setImageResource(R.drawable.ic_yellow_card);
            // }
        }
    }
}