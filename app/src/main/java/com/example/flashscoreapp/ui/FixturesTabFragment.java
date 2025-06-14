package com.example.flashscoreapp.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FixturesTabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup c, @Nullable Bundle s) {
        TextView textView = new TextView(getContext());
        textView.setText("Lịch thi đấu các trận sắp tới (sắp có)");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
