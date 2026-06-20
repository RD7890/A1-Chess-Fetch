package com.rohan.chessfetch.data.api

import com.rohan.chessfetch.data.model.ArchivesResponse
import com.rohan.chessfetch.data.model.GamesResponse
import com.rohan.chessfetch.data.model.Player
import com.rohan.chessfetch.data.model.PlayerStats
import retrofit2.http.GET
import retrofit2.http.Path

interface ChessApi {

    @GET("pub/player/{username}")
    suspend fun getPlayer(@Path("username") username: String): Player

    @GET("pub/player/{username}/stats")
    suspend fun getPlayerStats(@Path("username") username: String): PlayerStats

    @GET("pub/player/{username}/games/archives")
    suspend fun getArchives(@Path("username") username: String): ArchivesResponse

    @GET("pub/player/{username}/games/{year}/{month}")
    suspend fun getGames(
        @Path("username") username: String,
        @Path("year") year: String,
        @Path("month") month: String
    ): GamesResponse
}
