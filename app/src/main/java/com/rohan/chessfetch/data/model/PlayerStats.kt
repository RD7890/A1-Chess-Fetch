package com.rohan.chessfetch.data.model

import com.google.gson.annotations.SerializedName

data class PlayerStats(
    @SerializedName("chess_rapid") val rapid: ChessMode? = null,
    @SerializedName("chess_blitz") val blitz: ChessMode? = null,
    @SerializedName("chess_bullet") val bullet: ChessMode? = null,
    @SerializedName("chess_daily") val daily: ChessMode? = null,
    @SerializedName("tactics") val tactics: Tactics? = null
)

data class ChessMode(
    @SerializedName("last") val last: RatingEntry? = null,
    @SerializedName("best") val best: RatingEntry? = null,
    @SerializedName("record") val record: WinLossRecord? = null
)

data class RatingEntry(
    @SerializedName("rating") val rating: Int = 0,
    @SerializedName("date") val date: Long = 0,
    @SerializedName("rd") val rd: Int = 0
)

data class WinLossRecord(
    @SerializedName("win") val win: Int = 0,
    @SerializedName("loss") val loss: Int = 0,
    @SerializedName("draw") val draw: Int = 0
)

data class Tactics(
    @SerializedName("highest") val highest: RatingEntry? = null,
    @SerializedName("lowest") val lowest: RatingEntry? = null
)
