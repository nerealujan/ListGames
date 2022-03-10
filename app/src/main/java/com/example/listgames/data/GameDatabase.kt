package com.example.listgames.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listgames.model.dto.GameItemDTO

@Database(
    entities = [
        GameItemDTO::class],
    version = 20,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gamesDao(): GamesDao

    companion object {
        private const val DATABASE_NAME = "GAME_DATABASE"

        @Volatile
        private var gameDatabaseInstance: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase? {

            //make database
            if (gameDatabaseInstance == null) {
                synchronized(GameDatabase::class.java) {
                    if (gameDatabaseInstance == null) {
                        gameDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            GameDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }

            return gameDatabaseInstance
        }

    }
}