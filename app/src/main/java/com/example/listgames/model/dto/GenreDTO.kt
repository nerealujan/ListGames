package com.example.listgames.model.dto

import com.google.gson.annotations.SerializedName

data class GenreDTO (

    @SerializedName("id")
    var genreId: Int,
    @SerializedName("name")
    var name: String
)