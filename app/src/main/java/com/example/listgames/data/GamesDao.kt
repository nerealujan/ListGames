package com.example.listgames.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.listgames.model.dto.GameItemDTO

@Dao
interface GamesDao {


    @Query("SELECT * FROM games")
    fun getAllGames(): List<GameItemDTO>

    @Query("SELECT * FROM games WHERE id LIKE :gameId")
    fun getGameById(gameId: Int): GameItemDTO

    @Query("DELETE FROM games")
    fun deleteAllGames()

    @Insert
    fun insertGames(games: GameItemDTO)


}