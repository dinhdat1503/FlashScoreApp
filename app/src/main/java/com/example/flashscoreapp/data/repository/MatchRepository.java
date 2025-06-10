package com.example.flashscoreapp.data.repository;

// ... import các lớp cần thiết ...
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flashscoreapp.data.api.ApiService;
import com.example.flashscoreapp.data.api.RetrofitClient;
import com.example.flashscoreapp.data.model.ApiResponse;
import com.example.flashscoreapp.data.model.ApiMatch;
import com.example.flashscoreapp.data.model.League;
import com.example.flashscoreapp.data.model.Match;
import com.example.flashscoreapp.data.model.MatchDetails;
import com.example.flashscoreapp.data.model.Score;
import com.example.flashscoreapp.data.model.Team;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchRepository {

    private final ApiService apiService;
    // !! THAY THẾ BẰNG API KEY CỦA BẠN !!
    private final String API_KEY = "5ed9571131c54363cb3e39780ff22892";
    private final String API_HOST = "v3.football.api-sports.io";

    public MatchRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<Match>> getMatchesByDateFromApi(String date) {
        final MutableLiveData<List<Match>> data = new MutableLiveData<>();

        // In ra log để biết cuộc gọi API sắp được thực hiện
        android.util.Log.d("MatchRepository", "Attempting to fetch matches for date: " + date);

        apiService.getFixturesByDate(date, API_KEY, API_HOST).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                // Log này sẽ LUÔN chạy khi có phản hồi từ server
                android.util.Log.d("MatchRepository", "onResponse - Code: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<Match> convertedMatches = convertApiMatchesToDomain(response.body().getResponse());
                    android.util.Log.i("MatchRepository", "Successfully fetched and converted " + convertedMatches.size() + " matches.");
                    data.postValue(convertedMatches);
                } else {
                    // Nếu isSuccessful() là false, log lỗi chi tiết
                    android.util.Log.e("MatchRepository", "API Error - Message: " + response.message());
                    try {
                        // Cố gắng đọc nội dung lỗi từ server để biết chi tiết
                        String errorBody = response.errorBody().string();
                        android.util.Log.e("MatchRepository", "Error Body: " + errorBody);
                    } catch (Exception e) {
                        android.util.Log.e("MatchRepository", "Error parsing error body", e);
                    }
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Nếu có lỗi mạng, log chi tiết lỗi đó
                android.util.Log.e("MatchRepository", "Network Request Failed", t);
                data.postValue(null);
            }
        });
        return data;
    }

    // --- HÀM CHUYỂN ĐỔI DỮ LIỆU ---
    private List<Match> convertApiMatchesToDomain(List<ApiMatch> apiMatches) {
        List<Match> domainMatches = new ArrayList<>();
        if (apiMatches == null) return domainMatches;

        // Định dạng để chuyển đổi chuỗi ngày tháng từ API
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        for (ApiMatch apiMatch : apiMatches) {
            // Chuyển đổi các lớp con
            League league = new League(apiMatch.getLeague().getName());
            Team homeTeam = new Team(apiMatch.getTeams().getHome().getName(), apiMatch.getTeams().getHome().getLogo());
            Team awayTeam = new Team(apiMatch.getTeams().getAway().getName(), apiMatch.getTeams().getAway().getLogo());

            Integer homeGoals = apiMatch.getGoals().getHome();
            Integer awayGoals = apiMatch.getGoals().getAway();
            Score score = new Score(homeGoals != null ? homeGoals : 0, awayGoals != null ? awayGoals : 0);

            long matchTime = 0;
            try {
                Date parsedDate = sdf.parse(apiMatch.getFixture().getDate());
                if (parsedDate != null) {
                    matchTime = parsedDate.getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Tạo đối tượng Match mà ứng dụng của bạn sử dụng
            Match match = new Match(
                    apiMatch.getFixture().getId(),
                    league,
                    homeTeam,
                    awayTeam,
                    matchTime,
                    apiMatch.getFixture().getStatus().getShortStatus(),
                    score
            );
            domainMatches.add(match);
        }
        return domainMatches;
    }

    // Bạn có thể giữ lại các phương thức dùng mock data để tiện cho việc test
    public LiveData<List<Match>> getMatchesByDateFromMock(Context context, String date) {
        // ... giữ nguyên code cũ
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

    public LiveData<MatchDetails> getMatchDetailsFromMock(Context context, int matchId) {
        // ... giữ nguyên code cũ
        final MutableLiveData<MatchDetails> data = new MutableLiveData<>();
        String fileName = "mock_match_details_" + matchId + ".json";
        String jsonString = loadJSONFromAsset(context, fileName);

        if (jsonString != null) {
            Gson gson = new Gson();
            MatchDetails details = gson.fromJson(jsonString, MatchDetails.class);
            data.postValue(details);
        } else {
            data.postValue(null);
        }
        return data;
    }

    private String loadJSONFromAsset(Context context, String fileName) {
        // ... giữ nguyên code cũ
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

    // Bạn sẽ cần cập nhật các lớp model gốc (Match, Team, Score, League)
    // để có constructor phù hợp như tôi đã dùng ở trên
}