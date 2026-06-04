package com.practicum.playlistmaker.ui.navigation.helpers

object NavRoutes {
    fun trackDetails(trackId: Long) = "track_details/$trackId"
    fun playlistDetails(id: Long) = "playlist_details/$id"
    fun editPlaylist(id: Long) = "edit_playlist/$id"
}