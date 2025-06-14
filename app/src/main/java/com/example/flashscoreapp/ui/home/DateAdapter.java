package com.example.flashscoreapp.ui.home;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashscoreapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    public interface OnDateClickListener {
        void onDateClick(Calendar date);
    }

    private final List<Calendar> dates = new ArrayList<>();
    private int selectedPosition = -1;
    private final int todayPosition;
    private final OnDateClickListener listener;

    public DateAdapter(List<Calendar> dates, int todayPosition, OnDateClickListener listener) {
        this.dates.addAll(dates);
        this.todayPosition = todayPosition;
        this.selectedPosition = todayPosition;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        holder.bind(dates.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        private final TextView textDayOfWeek;
        private final TextView textDate;
        private final View indicator;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            textDayOfWeek = itemView.findViewById(R.id.text_day_of_week);
            textDate = itemView.findViewById(R.id.text_date);
            indicator = itemView.findViewById(R.id.view_indicator);

            itemView.setOnClickListener(v -> {
                int previousSelected = selectedPosition;
                selectedPosition = getAdapterPosition();
                if (listener != null && selectedPosition != RecyclerView.NO_POSITION) {
                    listener.onDateClick(dates.get(selectedPosition));
                    notifyItemChanged(previousSelected);
                    notifyItemChanged(selectedPosition);
                }
            });
        }

        public void bind(Calendar calendar, int position) {
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd.MM.", Locale.getDefault());
            textDate.setText(dayFormat.format(calendar.getTime()));

            String dayOfWeek;
            if (position == todayPosition) {
                dayOfWeek = "HÔM NAY";
            } else {
                switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY: dayOfWeek = "HAI"; break;
                    case Calendar.TUESDAY: dayOfWeek = "BA"; break;
                    case Calendar.WEDNESDAY: dayOfWeek = "TƯ"; break;
                    case Calendar.THURSDAY: dayOfWeek = "NĂM"; break;
                    case Calendar.FRIDAY: dayOfWeek = "SÁU"; break;
                    case Calendar.SATURDAY: dayOfWeek = "BẢY"; break;
                    case Calendar.SUNDAY: dayOfWeek = "CN"; break;
                    default: dayOfWeek = "";
                }
            }
            textDayOfWeek.setText(dayOfWeek);

            // Lấy màu text mặc định từ theme để tương thích sáng/tối
            TypedArray a = itemView.getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.textColorPrimary});
            int defaultTextColor = a.getColor(0, 0);
            a.recycle();

            // Lấy màu đỏ mới từ resource
            int selectedColor = ContextCompat.getColor(itemView.getContext(), R.color.selected_date_color);

            if (position == selectedPosition) {
                textDayOfWeek.setTextColor(selectedColor);
                textDate.setTextColor(selectedColor);
                indicator.setVisibility(View.VISIBLE);
            } else {
                if (position == todayPosition) {
                    // Chữ "HÔM NAY" khi không được chọn cũng sẽ có màu đỏ mới
                    textDayOfWeek.setTextColor(selectedColor);
                    textDate.setTextColor(selectedColor);
                } else {
                    textDayOfWeek.setTextColor(defaultTextColor);
                    textDate.setTextColor(defaultTextColor);
                }
                indicator.setVisibility(View.INVISIBLE);
            }
        }
    }
}