package com.example.flashscoreapp.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.model.MatchDetails; // Đảm bảo đã import MatchDetails
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class MatchRepository {

    /**
     * Phương thức này dùng cho màn hình chính (HomeFragment)
     * để lấy danh sách các trận đấu theo ngày.
     */
    public LiveData<List<Match>> getMatchesByDateFromMock(Context context, String date) {
        final MutableLiveData<List<Match>> data = new MutableLiveData<>();
        String jsonString = loadJSONFromAsset(context, "mock_database.json");

        if (jsonString != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, List<Match>>>(){}.getType();
            Map<String, List<Match>> allMatches = gson.fromJson(jsonString, type);
            List<Match> matchesForDate = allMatches.get(date);
            data.postValue(matchesForDate);
        } else {
            data.postValue(null);
        }
        return data;
    }

    /**
     * PHƯƠNG THỨC ĐANG BỊ THIẾU CỦA BẠN NẰM Ở ĐÂY.
     * Phương thức này dùng cho màn hình chi tiết (MatchDetailsActivity)
     * để lấy thông tin chi tiết của một trận đấu dựa vào matchId.
     */
    public LiveData<MatchDetails> getMatchDetailsFromMock(Context context, int matchId) {
        final MutableLiveData<MatchDetails> data = new MutableLiveData<>();
        // Tên file được tạo động dựa trên matchId, ví dụ: "mock_match_details_101.json"
        String fileName = "mock_match_details_" + matchId + ".json";
        String jsonString = loadJSONFromAsset(context, fileName);

        if (jsonString != null) {
            Gson gson = new Gson();
            MatchDetails details = gson.fromJson(jsonString, MatchDetails.class);
            data.postValue(details);
        } else {
            // Trường hợp không có file JSON chi tiết cho matchId này
            data.postValue(null);
        }
        return data;
    }

    /**
     * Hàm tiện ích để đọc file từ thư mục assets.
     */
    private String loadJSONFromAsset(Context context, String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}