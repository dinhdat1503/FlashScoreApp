package com.example.flashscoreapp.data.db;

import androidx.room.TypeConverter;
import com.example.flashscoreapp.data.model.domain.Match;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Converters {

    // Chuyển đổi từ đối tượng Match sang chuỗi JSON
    @TypeConverter
    public static String fromMatch(Match match) {
        if (match == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(match);
    }

    // Chuyển đổi từ chuỗi JSON về lại đối tượng Match
    @TypeConverter
    public static Match toMatch(String matchString) {
        if (matchString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Match>() {}.getType();
        return gson.fromJson(matchString, type);
    }
}