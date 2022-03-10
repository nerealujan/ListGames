package com.example.listgames

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.listgames.data.GameDatabase
import com.example.listgames.model.dto.GameItemDTO
import com.example.listgames.model.dto.GameList
import com.example.listgames.repository.GameRepository
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class GameViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val BODY = "fields name, screenshots.*, genres.name, age_ratings.rating, rating, summary;"
        private const val CLIENT_ID = "ikefu3gjaojsnnt21ik7orxyofnztq"
        private const val AUTHORIZATION = "Bearer 2uw3txw594lth0cxmlo2zpqlq1gdhv"
        private const val ACCEPT = "application/json"
    }

    private val mutableIsDataLoading: MutableLiveData<Boolean?> = MutableLiveData()
    private val mutableErrors = MutableLiveData<FuelError?>()

    val isDataLoading: LiveData<Boolean?> = mutableIsDataLoading

    private val repository = GameRepository(application.applicationContext)

    fun getGameById(gameId: Int): GameItemDTO {
        return repository.getGameById(gameId)
    }

    fun getGameItem(): ArrayList<GameItemDTO> = runBlocking(Dispatchers.Default){
        mutableIsDataLoading.postValue(true)
        var gamesItems = ArrayList<GameItemDTO>()
        "https://api.igdb.com/v4/games".httpPost()
            .header(
                "Client-ID" to CLIENT_ID,
                "Authorization" to AUTHORIZATION,
                "Accept" to ACCEPT
            )
            .body(BODY).responseString { _, _, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        mutableErrors.value = ex
                    }
                    is Result.Success -> {
                        val data = result.get()
                        gamesItems = Gson().fromJson(data, GameList::class.java)
                        Log.v(GameViewModel::class.java.simpleName, result.get())
                        repository.deleteAllGames()
                        for(i in 0..gamesItems.size - 1) {
                            repository.insertGame(gamesItems.get(i))
                        }
                    }
                }
            }.join()
        mutableIsDataLoading.postValue(false)
        return@runBlocking gamesItems
    }
}