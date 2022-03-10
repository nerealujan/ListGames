package com.example.listgames.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.listgames.model.GenreTC
import com.example.listgames.model.ScreenshotTC
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "games")
@TypeConverters(value = [
    GenreTC::class,
    ScreenshotTC::class
])
open class GameItemDTO (
    @PrimaryKey(autoGenerate = true)
    var uniqueId: Int,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int,
    @ColumnInfo(name = "name", index = true)
    @SerializedName("name")
    var name: String,
    @ColumnInfo(name = "genres", index = true)
    @SerializedName("genres")
    var genres: List<GenreDTO>,
    @ColumnInfo(name = "screenshots", index = true)
    @SerializedName("screenshots")
    var screenshots: List<ScreenshotsDTO>,
    @ColumnInfo(name = "summary", index = true)
    @SerializedName("summary")
    var summary: String
)

