package com.rohan.chessfetch.data.model

import com.google.gson.annotations.SerializedName

data class ArchivesResponse(
    @SerializedName("archives") val archives: List<String> = emptyList()
)

data class GamesResponse(
    @SerializedName("games") val games: List<Game> = emptyList()
)

data class Game(
    @SerializedName("url") val url: String = "",
    @SerializedName("pgn") val pgn: String? = null,
    @SerializedName("time_class") val timeClass: String = "",
    @SerializedName("time_control") val timeControl: String = "",
    @SerializedName("end_time") val endTime: Long = 0,
    @SerializedName("rated") val rated: Boolean = false,
    @SerializedName("white") val white: GamePlayer = GamePlayer(),
    @SerializedName("black") val black: GamePlayer = GamePlayer()
)

data class GamePlayer(
    @SerializedName("username") val username: String = "",
    @SerializedName("rating") val rating: Int = 0,
    @SerializedName("result") val result: String = "",
    @SerializedName("@id") val id: String? = null
)
