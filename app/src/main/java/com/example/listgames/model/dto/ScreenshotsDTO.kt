package com.example.listgames.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ScreenshotsDTO(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("game")
    var game: Int = 0,
    @SerializedName("image_id")
    var image_id: String = ""
)