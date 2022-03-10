package com.example.listgames.model

import androidx.room.TypeConverter
import com.example.listgames.model.dto.ScreenshotsDTO
import com.google.gson.Gson
import org.json.JSONArray

class ScreenshotTC {
    @TypeConverter
    fun listScreenshotToString(listScreenshot: List<ScreenshotsDTO>): String {
        return Gson().toJson(listScreenshot)
    }

    @TypeConverter
    fun stringToListScreenshot(string: String): List<ScreenshotsDTO> {
        return try {
            val jsonArray = JSONArray(string)
            val list = ArrayList<ScreenshotsDTO>()
            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)
                list.add(ScreenshotsDTO(jsonObject.getInt("id"),jsonObject.getInt("game"), jsonObject.getString("image_id")))
            }
            list
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}