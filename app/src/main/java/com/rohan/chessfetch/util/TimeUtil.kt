package com.rohan.chessfetch.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object TimeUtil {

    fun formatJoinDate(epochSeconds: Long): String {
        val date = Date(epochSeconds * 1000)
        return SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(date)
    }

    fun formatLastSeen(epochSeconds: Long): String {
        val now = System.currentTimeMillis() / 1000
        val diff = now - epochSeconds
        return when {
            diff < 60          -> "Just now"
            diff < 3600        -> "${diff / 60}m ago"
            diff < 86400       -> "${diff / 3600}h ago"
            diff < 2592000     -> "${diff / 86400}d ago"
            else               -> SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                                    .format(Date(epochSeconds * 1000))
        }
    }

    fun formatGameDate(epochSeconds: Long): String {
        val date = Date(epochSeconds * 1000)
        return SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date)
    }

    fun timeControlLabel(timeControl: String): String {
        return try {
            val secs = timeControl.split("+")[0].toLong()
            when {
                secs < 180  -> "Bullet"
                secs < 600  -> "Blitz"
                secs < 1800 -> "Rapid"
                else        -> "Classical"
            }
        } catch (e: Exception) { timeControl }
    }
}
