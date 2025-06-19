package com.example.flashscoreapp.data.api;

import com.example.flashscoreapp.data.model.remote.ApiFixture; // Giả sử model này tồn tại
import com.example.flashscoreapp.data.model.remote.ApiLeaguesResponse; // Giả sử model này tồn tại
import com.example.flashscoreapp.data.model.remote.ApiMatch; // Giả sử model này tồn tại
import com.example.flashscoreapp.data.model.remote.ApiResponse;
import com.example.flashscoreapp.data.model.remote.ApiResponsePlayers;
import com.example.flashscoreapp.data.model.remote.ApiStandingsResponse; // Giả sử model này tồn tại
import com.example.flashscoreapp.data.model.remote.ApiStatisticsResponse; // Giả sử model này tồn tại
import com.example.flashscoreapp.data.model.remote.ApiTopScorerData; // Giả sử model này tồn tại

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    // Trả về danh sách các trận đấu
    @GET("fixtures")
    Call<ApiResponse<ApiMatch>> getFixturesByDate(
            @Query("date") String date,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    // Trả về danh sách các trận đấu (cho chi tiết hoặc kết quả giải đấu)
    @GET("fixtures")
    Call<ApiResponse<ApiMatch>> getFixtures(
            @Query("id") Integer fixtureId,
            @Query("league") Integer leagueId,
            @Query("season") Integer seasonYear,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    // Trả về danh sách cầu thủ ghi bàn
    @GET("players/topscorers")
    Call<ApiResponse<ApiTopScorerData>> getTopScorers(
            @Query("season") int seasonYear,
            @Query("league") int leagueId,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("fixtures/statistics")
    Call<ApiStatisticsResponse> getMatchStatistics(
            @Query("fixture") int fixtureId,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("leagues")
    Call<ApiLeaguesResponse> getLeagues(
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("standings")
    Call<ApiStandingsResponse> getStandings(
            @Query("league") int leagueId,
            @Query("season") int season,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("fixtures")
    Call<ApiResponse<ApiMatch>> getFixturesByDateRange(
            @Query("from") String fromDate,
            @Query("to") String toDate,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("fixtures")
    Call<ApiResponse<ApiMatch>> getLiveFixtures(
            @Query("live") String live,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    /**
     * ĐÃ BỔ SUNG LẠI: Lấy đội hình của một đội bóng dựa trên ID của đội.
     * Hàm này cần thiết cho TeamRepository và chức năng Chi tiết đội bóng.
     */
    @GET("players/squads")
    Call<ApiResponsePlayers> getSquadByTeamId(
            @Query("team") final int teamId,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    // --- PHẦN ĐƯỢC SỬA ---
    @GET("fixtures")
    Call<ApiResponse<ApiMatch>> getFixturesForTeam(
            @Query("team") int teamId,
            @Query("from") String fromDate,
            @Query("to") String toDate,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );
}