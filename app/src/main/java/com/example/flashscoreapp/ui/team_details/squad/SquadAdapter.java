// app/src/main/java/com/example/flashscoreapp/ui/team_details/squad/SquadAdapter.java
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
import com.example.flashscoreapp.data.model.domain.PositionHeader; // Import PositionHeader
import com.example.flashscoreapp.data.model.domain.SquadListItem; // Import SquadListItem

/**
 * Adapter cho RecyclerView hiển thị danh sách đội hình (bao gồm tiêu đề vị trí và cầu thủ).
 */
public final class SquadAdapter extends ListAdapter<SquadListItem, RecyclerView.ViewHolder> { // Thay đổi kiểu dữ liệu thành SquadListItem và ViewHolder

    public SquadAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public int getItemViewType(final int position) {
        return getItem(position).getItemType(); // Trả về loại item (HEADER hoặc PLAYER)
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        if (viewType == SquadListItem.TYPE_HEADER) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_header, parent, false); // Sử dụng layout header có sẵn
            return new HeaderViewHolder(view);
        } else { // SquadListItem.TYPE_PLAYER
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
            return new PlayerViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final SquadListItem item = getItem(position);
        if (holder instanceof HeaderViewHolder) {
            final PositionHeader header = (PositionHeader) item;
            ((HeaderViewHolder) holder).bind(header);
        } else if (holder instanceof PlayerViewHolder) {
            final Player player = (Player) item;
            ((PlayerViewHolder) holder).bind(player);
        }
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
            // Chỉ hiển thị số áo nếu có
            if (player.getNumber() > 0) {
                playerNumber.setText(String.valueOf(player.getNumber()));
                playerNumber.setVisibility(View.VISIBLE);
            } else {
                playerNumber.setVisibility(View.GONE);
            }
            playerPosition.setText(player.getPosition());
            // Chỉ hiển thị tuổi nếu có
            if (player.getAge() > 0) {
                playerAge.setText(itemView.getContext().getString(R.string.player_age_format, player.getAge())); // Sử dụng string resource
                playerAge.setVisibility(View.VISIBLE);
            } else {
                playerAge.setVisibility(View.GONE);
            }


            Glide.with(itemView.getContext())
                    .load(player.getPhotoUrl())
                    .circleCrop()
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(playerPhoto);
        }
    }

    /**
     * Lớp ViewHolder cho tiêu đề vị trí.
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView headerTitle;

        HeaderViewHolder(@NonNull final View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.header_title); // ID này có trong item_group_header.xml
        }

        void bind(final PositionHeader header) {
            headerTitle.setText(header.getPositionName());
        }
    }

    private static final DiffUtil.ItemCallback<SquadListItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<SquadListItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull final SquadListItem oldItem, @NonNull final SquadListItem newItem) {
            if (oldItem.getItemType() != newItem.getItemType()) {
                return false;
            }

            if (oldItem.getItemType() == SquadListItem.TYPE_HEADER) {
                return ((PositionHeader) oldItem).getPositionName().equals(((PositionHeader) newItem).getPositionName());
            } else { // TYPE_PLAYER
                return ((Player) oldItem).getId() == ((Player) newItem).getId();
            }
        }

        @Override
        public boolean areContentsTheSame(@NonNull final SquadListItem oldItem, @NonNull final SquadListItem newItem) {
            if (oldItem.getItemType() != newItem.getItemType()) {
                return false;
            }

            if (oldItem.getItemType() == SquadListItem.TYPE_HEADER) {
                return oldItem.equals(newItem); // Dùng equals của PositionHeader
            } else { // TYPE_PLAYER
                return oldItem.equals(newItem); // Dùng equals của Player
            }
        }
    };
}