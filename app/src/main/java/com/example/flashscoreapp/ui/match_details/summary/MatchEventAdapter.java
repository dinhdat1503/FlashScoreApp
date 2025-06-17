package com.example.flashscoreapp.ui.match_details.summary;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.HalfHeader;
import com.example.flashscoreapp.data.model.domain.MatchEvent;

import java.util.ArrayList;
import java.util.List;

public class MatchEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_EVENT_HOME = 1;
    private static final int TYPE_EVENT_AWAY = 2;

    private List<Object> displayList = new ArrayList<>();
    private int homeTeamId;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MatchEvent> events, int homeTeamId) {
        this.homeTeamId = homeTeamId;
        this.displayList.clear();

        if (events == null || events.isEmpty()) {
            notifyDataSetChanged();
            return;
        }

        // Xử lý chèn Header Hiệp 1 và Hiệp 2
        boolean secondHalfHeaderAdded = false;
        displayList.add(new HalfHeader("HIỆP 1"));

        for (MatchEvent event : events) {
            // Giả sử hiệp 2 bắt đầu sau phút 45
            if (event.getMinute() > 45 && !secondHalfHeaderAdded) {
                displayList.add(new HalfHeader("HIỆP 2"));
                secondHalfHeaderAdded = true;
            }
            displayList.add(event);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = displayList.get(position);
        if (item instanceof HalfHeader) {
            return TYPE_HEADER;
        } else if (item instanceof MatchEvent) {
            MatchEvent event = (MatchEvent) item;
            return event.getTeamId() == homeTeamId ? TYPE_EVENT_HOME : TYPE_EVENT_AWAY;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflater.inflate(R.layout.item_event_half_header, parent, false));
            case TYPE_EVENT_HOME:
                return new EventViewHolder(inflater.inflate(R.layout.item_event_home, parent, false));
            case TYPE_EVENT_AWAY:
                return new EventViewHolder(inflater.inflate(R.layout.item_event_away, parent, false));
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.bind((HalfHeader) displayList.get(position));
        } else {
            EventViewHolder eventHolder = (EventViewHolder) holder;
            eventHolder.bind((MatchEvent) displayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    // ViewHolder cho Tiêu đề hiệp đấu
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView textHeader;
        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            textHeader = itemView.findViewById(R.id.text_half_header);
        }
        void bind(HalfHeader header) {
            textHeader.setText(header.getName());
        }
    }

    // ViewHolder chung cho Sự kiện (cả nhà và khách)
    static class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView textMinute, textPlayer;
        private final ImageView imageIcon;
        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            textMinute = itemView.findViewById(R.id.text_event_minute);
            textPlayer = itemView.findViewById(R.id.text_event_player);
            imageIcon = itemView.findViewById(R.id.image_event_icon);
        }

        void bind(MatchEvent event) {
            String minuteText = event.getMinute() + "'";
            textMinute.setText(minuteText);

            // TODO: Xử lý hiển thị tên cầu thủ thay người chi tiết hơn ở đây
            String playerText = event.getPlayer();
            textPlayer.setText(playerText);

            imageIcon.setVisibility(View.VISIBLE);
            String eventType = event.getType();
            if ("Goal".equalsIgnoreCase(eventType)) {
                imageIcon.setImageResource(R.drawable.ic_football_goal);
            } else if ("Yellow Card".equalsIgnoreCase(eventType)) {
                imageIcon.setImageResource(R.drawable.ic_yellow_card);
            } else if ("Red Card".equalsIgnoreCase(eventType)) {
                imageIcon.setImageResource(R.drawable.ic_red_card);
            } else { // "subst" và các loại khác
                imageIcon.setImageResource(R.drawable.ic_leagues_24); // Tạm thời dùng icon thay người
            }
        }
    }
}