package com.practicum.playlistmaker.ui.navigation.helpers

object NavRoutes {
    const val TRACK_DETAILS = "track_details/{trackId}"
    const val PLAYLIST_DETAILS = "playlist_details/{playlistId}"

    fun trackDetails(trackId: Long) = "track_details/$trackId"

    fun playlistDetails(id: Long) = "playlist_details/$id"
}