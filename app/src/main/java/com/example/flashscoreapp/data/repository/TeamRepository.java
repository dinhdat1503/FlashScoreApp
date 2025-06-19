package com.example.flashscoreapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.flashscoreapp.data.api.ApiService;
import com.example.flashscoreapp.data.model.domain.Player;
import com.example.flashscoreapp.data.model.remote.ApiPlayerInSquad;
import com.example.flashscoreapp.data.model.remote.ApiResponsePlayers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository chịu trách nhiệm xử lý các nguồn dữ liệu liên quan đến Đội bóng (Team).
 */
public final class TeamRepository {
    private final ApiService apiService;
    // TODO: Thay thế các giá trị placeholder bằng API key và host thật của bạn.
    // Nên lưu trữ các giá trị này trong file build.gradle hoặc một lớp hằng số riêng.
    private final String API_KEY = "5e88b7e40emsh79a567711143f87p119b30jsnbc4b0f951a5";
    private final String API_HOST = "api-football-v1.p.rapidapi.com";


    public TeamRepository(final ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Lấy danh sách đội hình của một đội từ API.
     * @param teamId ID của đội bóng.
     * @return LiveData chứa danh sách các cầu thủ.
     */
    public LiveData<List<Player>> getSquad(final int teamId) {
        final MutableLiveData<List<Player>> squadLiveData = new MutableLiveData<>();

        // Truyền thêm API Key và Host vào lệnh gọi
        apiService.getSquadByTeamId(teamId, API_KEY, API_HOST).enqueue(new Callback<ApiResponsePlayers>() {
            @Override
            public void onResponse(@NonNull final Call<ApiResponsePlayers> call, @NonNull final Response<ApiResponsePlayers> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResponse() != null) {
                    final List<ApiPlayerInSquad> apiPlayers = response.body().getResponse();
                    final List<Player> domainPlayers = mapApiPlayersToDomain(apiPlayers);
                    squadLiveData.postValue(domainPlayers);
                } else {
                    squadLiveData.postValue(Collections.emptyList()); // Trả về list rỗng thay vì null
                }
            }

            @Override
            public void onFailure(@NonNull final Call<ApiResponsePlayers> call, @NonNull final Throwable t) {
                squadLiveData.postValue(Collections.emptyList()); // Trả về list rỗng thay vì null
            }
        });

        return squadLiveData;
    }

    /**
     * Ánh xạ danh sách cầu thủ từ Remote model (API) sang Domain model (ứng dụng).
     * @param apiPlayers Danh sách cầu thủ từ API.
     * @return Danh sách cầu thủ theo mô hình của ứng dụng.
     */
    private List<Player> mapApiPlayersToDomain(final List<ApiPlayerInSquad> apiPlayers) {
        if (apiPlayers == null) {
            return Collections.emptyList();
        }

        final List<Player> domainList = new ArrayList<>();
        for (final ApiPlayerInSquad apiPlayer : apiPlayers) {
            domainList.add(new Player(
                    apiPlayer.getId(),
                    apiPlayer.getName(),
                    apiPlayer.getAge(),
                    apiPlayer.getNumber(),
                    apiPlayer.getPosition(),
                    apiPlayer.getPhoto()
            ));
        }
        return domainList;
    }
}
