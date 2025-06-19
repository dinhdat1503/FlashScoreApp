package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Lớp bao bọc (wrapper) cho response từ API khi gọi lấy danh sách cầu thủ.
 * API thường trả về một đối tượng JSON gốc chứa một danh sách bên trong.
 */
public final class ApiResponsePlayers {
    @SerializedName("response")
    private List<ApiPlayerInSquad> response;

    public List<ApiPlayerInSquad> getResponse() {
        return response;
    }
}
