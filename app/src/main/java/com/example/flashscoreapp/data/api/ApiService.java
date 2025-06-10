package com.example.flashscoreapp.data.api;

import com.example.flashscoreapp.data.model.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    @GET("fixtures") // Endpoint để lấy các trận đấu
    Call<ApiResponse> getFixturesByDate(
            @Query("date") String date,
            @Header("x-rapidapi-key") String apiKey,
            @Header("x-rapidapi-host") String apiHost
    );
}