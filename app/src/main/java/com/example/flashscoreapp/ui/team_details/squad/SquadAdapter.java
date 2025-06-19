package com.example.flashscoreapp.ui.team_details.squad;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flashscoreapp.R;
import com.example.flashscoreapp.data.model.domain.Player;

/**
 * Adapter cho RecyclerView hiển thị danh sách cầu thủ trong đội hình.
 */
public final class SquadAdapter extends ListAdapter<Player, SquadAdapter.PlayerViewHolder> {

    public SquadAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerViewHolder holder, final int position) {
        final Player player = getItem(position);
        holder.bind(player);
    }

    /**
     * Lớp ViewHolder cho một item cầu thủ.
     */
    static class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView playerPhoto;
        private final TextView playerNumber;
        private final TextView playerName;
        private final TextView playerPosition;
        private final TextView playerAge;

        PlayerViewHolder(@NonNull final View itemView) {
            super(itemView);
            playerPhoto = itemView.findViewById(R.id.player_photo);
            playerNumber = itemView.findViewById(R.id.player_number);
            playerName = itemView.findViewById(R.id.player_name);
            playerPosition = itemView.findViewById(R.id.player_position);
            playerAge = itemView.findViewById(R.id.player_age);
        }

        void bind(final Player player) {
            playerName.setText(player.getName());
            playerNumber.setText(String.valueOf(player.getNumber()));
            playerPosition.setText(player.getPosition());
            playerAge.setText(String.valueOf(player.getAge()));

            Glide.with(itemView.getContext())
                    .load(player.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(playerPhoto);
        }
    }

    private static final DiffUtil.ItemCallback<Player> DIFF_CALLBACK = new DiffUtil.ItemCallback<Player>() {
        @Override
        public boolean areItemsTheSame(@NonNull final Player oldItem, @NonNull final Player newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull final Player oldItem, @NonNull final Player newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getNumber() == newItem.getNumber();
        }
    };
}
