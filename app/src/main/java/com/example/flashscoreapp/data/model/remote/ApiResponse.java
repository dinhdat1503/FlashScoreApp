package com.example.flashscoreapp.data.model.remote;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// Sử dụng Generic Type <T> để lớp này có thể chứa bất kỳ loại dữ liệu nào
public class ApiResponse<T> {
    @SerializedName("response")
    private List<T> response;

    public List<T> getResponse() {
        return response;
    }
}