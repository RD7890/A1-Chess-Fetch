package com.rohan.chessfetch.data.repository

import com.rohan.chessfetch.data.api.ChessApi
import com.rohan.chessfetch.data.model.Game
import com.rohan.chessfetch.data.model.Player
import com.rohan.chessfetch.data.model.PlayerStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChessRepository @Inject constructor(
    private val api: ChessApi
) {
    fun getPlayer(username: String): Flow<Result<Player>> = flow {
        try {
            emit(Result.success(api.getPlayer(username.lowercase().trim())))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getPlayerStats(username: String): Flow<Result<PlayerStats>> = flow {
        try {
            emit(Result.success(api.getPlayerStats(username.lowercase().trim())))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getRecentGames(username: String): Flow<Result<List<Game>>> = flow {
        try {
            val archives = api.getArchives(username.lowercase().trim())
            if (archives.archives.isEmpty()) {
                emit(Result.success(emptyList<Game>()))
                return@flow
            }
            val latest = archives.archives.last()
            val parts = latest.split("/")
            val month = parts[parts.size - 1]
            val year = parts[parts.size - 2]
            val response = api.getGames(username.lowercase().trim(), year, month)
            emit(Result.success(response.games.sortedByDescending { it.endTime }))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
