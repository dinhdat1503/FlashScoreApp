    package com.example.flashscoreapp.data.api;

    import com.example.flashscoreapp.data.model.remote.ApiLeaguesResponse;
    import com.example.flashscoreapp.data.model.remote.ApiMatch;
    import com.example.flashscoreapp.data.model.remote.ApiResponse;
    import com.example.flashscoreapp.data.model.remote.ApiResponsePlayers;
    import com.example.flashscoreapp.data.model.remote.ApiStandingsResponse;
    import com.example.flashscoreapp.data.model.remote.ApiStatisticsResponse;
    import com.example.flashscoreapp.data.model.remote.ApiTopScorerData;

    import retrofit2.Call;
    import retrofit2.http.GET;
    import retrofit2.http.Header;
    import retrofit2.http.Query;

    public interface ApiService {

        // ... (các phương thức khác giữ nguyên)
        @GET("fixtures")
        Call<ApiResponse<ApiMatch>> getFixturesByDate(
                @Query("date") String date,
                @Header("x-rapidapi-key") String apiKey,
                @Header("x-rapidapi-host") String apiHost
        );

        @GET("fixtures")
        Call<ApiResponse<ApiMatch>> getFixtures(
                @Query("id") Integer fixtureId,
                @Query("league") Integer leagueId,
                @Query("season") Integer seasonYear,
                @Header("x-rapidapi-key") String apiKey,
                @Header("x-rapidapi-host") String apiHost
        );

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
                @Query("season") int season,
                @Header("x-rapidapi-key") String apiKey,
                @Header("x-rapidapi-host") String apiHost
        );

        @GET("players/squads") // Endpoint để lấy đội hình
        Object getTeamSquad(@Query("team") int teamId);
    }