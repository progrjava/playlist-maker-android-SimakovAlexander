package com.practicum.playlistmaker.ui.navigation.helpers

object NavRoutes {
    const val TRACK_DETAILS = "track_details/{trackId}"

    fun trackDetails(trackId: Long) = "track_details/$trackId"
}