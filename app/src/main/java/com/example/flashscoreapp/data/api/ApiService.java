package com.example.flashscoreapp.data.api;

import com.example.flashscoreapp.data.model.ApiResponse;
import com.example.flashscoreapp.data.model.ApiStatisticsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("fixtures")
    Call<ApiResponse> getFixturesByDate(
            @Query("date") String date,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );


    // Endpoint này sẽ trả về thông tin chi tiết của một trận đấu, bao gồm cả các sự kiện (events)
    @GET("fixtures")
    Call<ApiResponse> getMatchEvents(
            @Query("id") int fixtureId,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );

    @GET("fixtures/statistics")
    Call<ApiStatisticsResponse> getMatchStatistics(
            @Query("fixture") int fixtureId,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );
}