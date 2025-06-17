package com.example.flashscoreapp.ui.match_details.statistics;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MatchStatisticsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        TextView textView = new TextView(getContext());
        textView.setText("Nội dung tab Số liệu (sắp có)");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}