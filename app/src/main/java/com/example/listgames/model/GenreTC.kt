package com.example.listgames.model

import androidx.room.TypeConverter
import com.example.listgames.model.dto.GenreDTO
import com.google.gson.Gson
import org.json.JSONArray

class GenreTC {
    @TypeConverter
    fun listGenreToString(listGenre: List<GenreDTO>): String {
        return Gson().toJson(listGenre)
    }

    @TypeConverter
    fun stringToListGenre(string: String): List<GenreDTO> {
        return try {
            val jsonArray = JSONArray(string)
            val list = ArrayList<GenreDTO>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(GenreDTO(jsonObject.getInt("id"), jsonObject.getString("name")))
            }
            list
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}