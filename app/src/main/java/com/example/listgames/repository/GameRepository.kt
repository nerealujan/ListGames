package com.example.listgames.repository

import android.content.Context
import com.example.listgames.data.GameDatabase
import com.example.listgames.data.GamesDao
import com.example.listgames.model.dto.GameItemDTO

class GameRepository(context: Context) {
    private val gameDao: GamesDao

    init {
        val database = GameDatabase.getDatabase(context)
        gameDao = database!!.gamesDao()
    }

    fun getAllGames(): List<GameItemDTO> {
        return gameDao.getAllGames()
    }

    fun deleteAllGames() {
        gameDao.deleteAllGames()
    }

    fun insertGame(game: GameItemDTO) {
        gameDao.insertGames(game)
    }

    fun getGameById(id: Int): GameItemDTO {
        return gameDao.getGameById(id)
    }

}