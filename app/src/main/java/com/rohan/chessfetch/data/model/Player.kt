package com.rohan.chessfetch.data.model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("player_id") val playerId: Long = 0,
    @SerializedName("username") val username: String = "",
    @SerializedName("title") val title: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("followers") val followers: Int = 0,
    @SerializedName("country") val country: String? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("joined") val joined: Long = 0,
    @SerializedName("last_online") val lastOnline: Long = 0,
    @SerializedName("status") val status: String = ""
)
